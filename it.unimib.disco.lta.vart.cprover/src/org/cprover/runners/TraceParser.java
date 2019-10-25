package org.cprover.runners;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.cprover.communication.Assertion;
import org.cprover.communication.Assignment;
import org.cprover.communication.Location;
import org.cprover.communication.Result;
import org.cprover.communication.Step;
import org.cprover.communication.Trace;
import org.cprover.core.CProverPlugin;
import org.eclipse.core.internal.utils.FileUtil;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parses XML trace from SATABS.
 * 
 * Ok this is pretty messy and understandable...
 * 
 * @author Gérard Basler
 */
public class TraceParser extends DefaultHandler {

	private static final String CPROVER = "cprover";

	private static final String CPROVER_STATUS = "cprover-status";
    private static final String GOTO_TRACE = "goto_trace";
    private static final String LOCATION_ONLY = "location-only";
    private static final String ASSIGNMENT = "assignment";
    
    private static final String NAMED_SUB = "named_sub";
    private static final String LOCATION = "location";
    private static final String THREAD = "thread";
    private static final String TYPE = "type";
    private static final String VALUE = "value";
    private static final String IDENTIFIER = "identifier";
    
    private static final String ID = "id";
    private static final String FILE = "file";
    private static final String LINE = "line";
    private static final String NAME = "name";
    
    private static final String FAILURE = "failure";
    private static final String SUCCESS = "SUCCESS";

    private static final String MESSAGE = "MESSAGE";
    private static final String TEXT = "TEXT";

    private static final String ERROR = "ERROR";

	protected static final boolean WORK_ON_REAL_SOURCE = true;
    
    private String sourceFolder = "";
    
    public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	/**
     * Parsed trace, if any.
     */
    private Trace trace;
    
    /**
     * Result of SATABS run.
     */
    private Result result = Result.UNKNOWN;

    /**
     * Delegates handling.
     */
    private LinkedList<DelayedHandler> tagHandler = new LinkedList<DelayedHandler>();

    /**
     */
    public TraceParser() {
    }
    
    /**
     * Simple interface that is used to read values of strings etc at some later point.
     * 
     * @author Gérard Basler
     */
    interface IDelayedValueReader {
    	void take(String value);
    }
    
    interface IDone {
        void done();
    }
    
    private abstract class DelayedHandler extends DefaultHandler {
        
        protected IDelayedValueReader delayed;
        
        protected IDone done;
        
        /**
         * @param done the done to set
         */
        public void setDone(IDone done) {
            assert( done == null );
            this.done = done;
        }
        
        public void removing() {
            if( done != null ) {
                done.done();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if( delayed != null ) {
                String str = new String(ch, start, length);
                delayed.take(str);
            }
		}
    }

    private final class FailureStepHandler extends DelayedHandler {
        
        Step step = new Step();
        
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            if( qName.equalsIgnoreCase(LOCATION) ) {
                assert( step != null );
                step.location = new Location();
                addHandler( new LocationHandler(step.location) );
            }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase(FAILURE)) {
//                assert (trace != null);
//                assert (step != null);
//                if (step.location != null) {
//                    trace.steps.addFirst(step);
//                }
                removeHandler();
                step = null;
            }
        }
    }

    private final class LocationStepHandler extends DelayedHandler {
        
        Step step = new Step();
        
        public void startElement(String uri, String localName, String qName, Attributes attributes)
        		throws SAXException {
            if( qName.equalsIgnoreCase(LOCATION) ) {
                assert( step != null );
                step.location = new Location();
                addHandler( new LocationHandler(step.location) );
            }
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase(LOCATION_ONLY)) {
//                assert (trace != null);
//                assert (step != null);
//                if (step.location != null) {
//                    trace.steps.addFirst(step);
//                }
                removeHandler();
                step = null;
            }
        }
    }

    private final class AssignmentStepHandler extends DelayedHandler {
        
        private static final boolean CBMC_5 = false;
		Assignment assign = new Assignment();
        
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
        		throws SAXException {
        	if( qName.equalsIgnoreCase(IDENTIFIER) ) {
        		assert( assign != null );
        		assert( delayed == null );
        		delayed = new IDelayedValueReader() {
        			public void take(String value) {
        				assign.identifier += value;
        			}
        		};
        	}
        	else if( qName.equalsIgnoreCase(VALUE) ) {
        		assert( assign != null );
        		assert( delayed == null );
        		delayed = new IDelayedValueReader() {
        			public void take(String value) {
        				assign.value += value;
        			}
        		};
        	}
        	else if( qName.equalsIgnoreCase(TYPE) ) {
        		assert( assign != null );
        		assert( delayed == null );
        		delayed = new IDelayedValueReader() {
        			public void take(String value) {
        				assign.type += value;
        			}
        		};
        	}
        	else if( qName.equalsIgnoreCase(THREAD) ) {
        		assert( assign != null );
        		assert( delayed == null );
        		delayed = new IDelayedValueReader() {
        			public void take(String value) {
        				assign.threadId += value;
        			}
        		};
        	}
        	else if( qName.equalsIgnoreCase(LOCATION) ) {
        	    assert( assign != null );
        	    assign.location = new Location();
        	    if ( CBMC_5 ){
        	    	assign.location.file = attributes.getValue("file");
        	    	
        	    	String val = attributes.getValue("line");
        	    	assign.location.line = val.equals("") ? 0 : Integer.valueOf(val);
        	    } else {
        	    	addHandler( new LocationHandler(assign.location) );
        	    }
        	}
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
        	if( qName.equalsIgnoreCase(ASSIGNMENT) ) {
//        		assert( trace != null );
//        		assert( assign != null );
//        		if( assign.location != null ) {
//        			trace.steps.addFirst( assign );
//        		}
        		removeHandler();
        		assign = null;
        	}
        	delayed = null;
        }
    }

    private final class LocationHandler extends DelayedHandler implements IDone {

        private Location location;
        
        private NamedSubHandler h;
        
        public LocationHandler(Location location) {
            this.location = location;
        }

        public void done() {
            if( h.name == null ) return;
            
            if( h.name.equalsIgnoreCase(LINE) ) {
                location.line = Integer.parseInt( h.id );
            }
            else if( h.name.equalsIgnoreCase(FILE) ) {
                location.file = sourceFolder+h.id;
            }
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if( qName.equalsIgnoreCase(NAMED_SUB) ) {
                h = new NamedSubHandler(uri, localName, qName, attributes); 
                h.setDone( this );
                addHandler( h );
            }
        	// NH
            if (qName.equalsIgnoreCase(FILE)) {
                assert( delayed == null );
                delayed = new IDelayedValueReader() {
                    public void take(String value) {
                    	location.file = sourceFolder+value;
                    }
                };
            }
            if (qName.equalsIgnoreCase(LINE)) {
                assert( delayed == null );
                delayed = new IDelayedValueReader() {
                    public void take(String value) {
                    	if(location.file.equalsIgnoreCase(">"))
                    		location.file = "built-in";
                        location.line = Integer.parseInt( value );
                    }
                };
            }            
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            if( qName.equalsIgnoreCase(LOCATION) ) {
            	assert( location != null );
            	assert( location.line != -1 );
            	assert( location.file != null );
            	
            	Integer mapper[] = getLineMapper( location.file );
            	if ( mapper != null ){
            		System.out.println(location.line);
            		location.file = mapperFile.get(location.file);
            		Integer l = mapper[location.line];
            		
            		System.out.println("CORR "+l);
            		location.line = l;
            	}
                removeHandler();
            }
            delayed = null;
        }
        
    }
    
    HashMap<String,Integer[]> mapper = new HashMap<String,Integer[]>();
    HashMap<String,String> mapperFile = new HashMap<String,String>();
    private Integer[] getLineMapper( String file ) {
    	
    	Integer[] _res = mapper.get(file);
    	if ( _res != null ){
    		//return _res;
    	}
    	
    	File f = new File ( file+".mapper" );
    	
    	if ( ! f.exists() ){
    		return null;
    	}
    	
    	try {
			BufferedReader r = new BufferedReader(new FileReader(f) );
			String line;
			
			ArrayList<Integer> res = new ArrayList<Integer>();
			while ( ( line = r.readLine() ) != null ){
				System.out.println("L "+line);
				if ( res.size() == 0 ){
					res.add(0);
					mapperFile.put(file, line);
				} else {
					if ( line.isEmpty() ){
						break;
					}
					
					res.add(Integer.valueOf(line));
				}
			}
			
			System.out.println(res);
			Integer[] array = res.toArray(new Integer[res.size()]);
			mapper.put(file, array);
			
			return array;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    private final class NamedSubHandler extends DelayedHandler {

        String name;

        String id = "";

        public NamedSubHandler(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            this.name = attributes.getValue(NAME);
            assert( !this.name.equals("") );
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase(ID)) {
                assert( delayed == null );
                delayed = new IDelayedValueReader() {
                    public void take(String value) {
                        id += value;
                    }
                };
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase(NAMED_SUB)) {
            	assert( name != null );
            	assert( id != null );
                removeHandler();
            }
            delayed = null;
        }
    }
    
    private final class StatusHandler extends DelayedHandler {

        public StatusHandler() {
            delayed = new IDelayedValueReader() {
                public void take(String value) {
                        if (value.equalsIgnoreCase(FAILURE)) {
                            result = Result.FAILURE;
                        }
                        else if (value.equalsIgnoreCase(SUCCESS)) {
                            result = Result.SUCCESS;
                        }
                }
            };
        }
        
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase(CPROVER_STATUS)) {
                removeHandler();
            }
            delayed = null;
        }
    }

    private final class MessageHandler extends DelayedHandler {
        
        private String type;
        
        private String error = "";
        
        public MessageHandler(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            this.type = attributes.getValue(TYPE);
            if( type.equalsIgnoreCase(ERROR) ) {
                result = Result.ERROR;
            }
        }
        
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if( qName.equalsIgnoreCase(TEXT) && type.equalsIgnoreCase(ERROR) ) {
                assert( delayed == null );
                delayed = new IDelayedValueReader() {
                    public void take(String value) {
                        error += value;
                    }
                };
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase(MESSAGE)) {
                removeHandler();
            }
            delayed = null;
        }
    }

    @Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
        if( !tagHandler.isEmpty() ) {
			tagHandler.getLast().startElement(uri, localName, qName, attributes);
			return;
		}
		
		if( qName.equalsIgnoreCase(GOTO_TRACE) ) {
			assert( getTrace() == null );
			this.trace = new Trace();
		}
		else if( qName.equalsIgnoreCase(ASSIGNMENT) ) {
		    final AssignmentStepHandler h = new AssignmentStepHandler();
		    h.setDone( new IDone() {
                public void done() {
                    assert( h.assign != null );
                    boolean add = false;
                    if ( WORK_ON_REAL_SOURCE ){
                    	if ( ! assignOnTempVariable( h.assign.identifier ) ){
                    		add=true;
                    	}
                    } else {
                    	add=true;
                    }
                    
                    if ( add ) {
                    	getTrace().steps.addLast( h.assign );
                    }
                }

				private boolean assignOnTempVariable(String identifier) {
					return identifier.contains("_ret_");
				}
            });
		    addHandler( h );
		}
		else if( qName.equalsIgnoreCase(LOCATION_ONLY) ) {
		    final LocationStepHandler h = new LocationStepHandler();
		    h.setDone( new IDone() {
                public void done() {
                    assert( h.step != null );
                    getTrace().steps.addLast( h.step );
                }
		    });
		    addHandler( h );
		}
		else if( qName.equalsIgnoreCase(FAILURE) ) {
		    final FailureStepHandler h = new FailureStepHandler();
		    h.setDone( new IDone() {
		        public void done() {
		            assert( h.step != null );
		            getTrace().steps.addLast( h.step );
		        }
		    });
		    addHandler( h );
		}
		else if( qName.equalsIgnoreCase(CPROVER_STATUS) ) {
		    final StatusHandler h = new StatusHandler();
		    addHandler( h );
		}
		else if( qName.equalsIgnoreCase(MESSAGE) ) {
		    final MessageHandler h = new MessageHandler(uri, localName, qName, attributes);
		    addHandler( h );
		}
	}

    @Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if( !tagHandler.isEmpty() ) {
			tagHandler.getLast().endElement(uri, localName, qName);
		}
	}

    @Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if( !tagHandler.isEmpty() ) {
			tagHandler.getLast().characters(ch, start, length);
		}
	}

	protected void addHandler(DelayedHandler locationStepHandler) {
        assert( tagHandler.isEmpty() );
        tagHandler.addLast( locationStepHandler );
    }

    protected void removeHandler() {
        tagHandler.getLast().removing();
        tagHandler.removeLast();
    }
    
    public Result parse(String xml) throws CoreException {
    	SAXParserFactory factory = SAXParserFactory.newInstance();
    	SAXParser saxParser;
    	try {
    		saxParser = factory.newSAXParser();
    		saxParser.parse(new ByteArrayInputStream(xml.getBytes()), this);
        	removeUnneededSteps();	
    	} catch (ParserConfigurationException e) {
			throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, e.getCause().getMessage(), null));
//    		Utils.showErrorDialog(CProverPlugin.getActiveWorkbenchShell(), "", e.toString(), Utils.NORMAL);
    	} catch(SAXParseException e) {
//    		String[] lines = xml.split("\n");
//    		String line = lines[e.getLineNumber()-1];
//    		Utils.showErrorDialog(CProverPlugin.getActiveWorkbenchShell(), e.toString(), line, Utils.NORMAL);
			throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, e.getCause().getMessage(), null));
    	} catch (SAXException e) {
//    		Utils.showErrorDialog(CProverPlugin.getActiveWorkbenchShell(), "", e.toString(), Utils.NORMAL);
			throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, e.getCause().getMessage(), null));
    	} catch (IOException e) {
//    		Utils.showErrorDialog(CProverPlugin.getActiveWorkbenchShell(), "", e.toString(), Utils.NORMAL);
			throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, e.getCause().getMessage(), null));
    	}
    	
    	return result;
    }
    
    /**
     * SATABS etc produce too many intermediate steps, we need to filter them out.
     */
    protected void removeUnneededSteps() {
        if( trace == null ) return;
        
        Set<Location> locations = new HashSet<Location>();
        LinkedList<Step> shortTrace = new LinkedList<Step>();
        Step postponed = null;
        for (Step step : trace.steps) {

            if (postponed != null && !postponed.location.equals(step.location) ) {
                shortTrace.add(postponed);
            }
            postponed = null;

            if (step instanceof Assignment || step instanceof Assertion) {
                // assignment in the middle
                locations.clear();
                if( step.location != null ) {
                    locations.add(step.location);
                    shortTrace.add(step);
                }
            } else {
                // just normal step
                if (!locations.contains(step.location)) {
                    // don't add right now because the next Assignment might have same location
                    postponed = step;
                }
            }
        }
        if (postponed != null) {
            shortTrace.add(postponed);
        }
        trace.steps = shortTrace;
    }

    public Trace getTrace() {
        return trace;
    }

    /**
     * @return the result
     */
    public Result getResult() {
        return result;
    }
    
}