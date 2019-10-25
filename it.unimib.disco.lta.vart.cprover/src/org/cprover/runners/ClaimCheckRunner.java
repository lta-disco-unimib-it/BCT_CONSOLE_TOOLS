package org.cprover.runners;

import java.util.Collections;
import java.util.LinkedList;

import org.cprover.communication.Claim;
import org.cprover.communication.Loop;
import org.cprover.communication.Result;
import org.cprover.communication.Trace;
import org.cprover.core.CProverPlugin;
import org.cprover.core.StreamsProxy;
import org.cprover.launch.VerifyConfig;
import org.cprover.launch.VerifyConfig.Verifier;
import org.cprover.model.CProverProcess;
import org.cprover.model.VerifyDebugTarget;
import org.cprover.model.VerifySourceLocator;
import org.cprover.model.VerifyThread;
import org.cprover.views.ClaimsView;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;

/**
 * Runs a single claim and shows it in debug view.
 * 
 * @author Gérard Basler
 */
public class ClaimCheckRunner extends AbstractRunner {

    /**
     * Claim that will / has been checked by this runner.
     */
    public Claim claim;

    public ShowClaimsRunner showClaimsRunner;

    public boolean waiting;

    private boolean running;

    //private CProverProcess process;

    /**
     * Root directory of project. Used to cut filenames.
     */
    private String root;

    /**
     * Trace, if there is one.
     */
    private Trace trace;

	private String sourceFolder;

	private String gotoCCprogram;

	private int unwind;

	
	
    public void setGotoCCprogram(String gotoCCprogram) {
		this.gotoCCprogram = gotoCCprogram;
	}

	public ClaimCheckRunner(VerifyConfig config, ILaunch launch, Claim claim, ShowClaimsRunner showClaimsRunner) {
        super(config, launch);
        this.claim = claim;
        this.root = config.workingDirectory.toString();
        this.showClaimsRunner = showClaimsRunner;
        running = false;
        this.waiting = false;
    }

    public void exec() throws CoreException {
        // Map<String, String> variables = System.getenv();
        // for (Map.Entry<String, String> entry : variables.entrySet()) {
        // String name = entry.getKey();
        // String value = entry.getValue();
        // System.out.println(name + "=" + value);
        // }

    	String[] cmd = getCommand();
        Process checkProcess = DebugPlugin.exec( cmd, config.workingDirectory, config.envp );
        CProverProcess process = (CProverProcess) DebugPlugin.newProcess(launch, checkProcess, "Checking claim: " + claim.id + " (" + claim.description + ")");
        
        //System.out.println("exec() check: checkP: " + checkProcess.toString() + " P: " + process.toString());
        // MessageConsole console = new MessageConsole(getConsoleName(), null);
        // console.activate();
        // ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ console });
        // MessageConsoleStream stream = console.newMessageStream();
        // stream.println("Hello, world!");

        StreamsProxy streamsProxy = (StreamsProxy) process.getStreamsProxy();
        IDebugEventSetListener listener = new ClaimsRunnerDebugEventListener(streamsProxy);
        DebugPlugin.getDefault().addDebugEventListener(listener);

        running = true;

//        ClaimsView.refreshClaim(claim);
        //System.out.println("\nExecute");
    }

    private class ClaimsRunnerDebugEventListener implements IDebugEventSetListener {

        private StreamsProxy streamsProxy;
        public ClaimsRunnerDebugEventListener(StreamsProxy streamsProxy) {
            this.streamsProxy = streamsProxy;
        }

        public void handleDebugEvents(DebugEvent[] events) {
            for (int i = 0; i < events.length; i++) {
            	
            	if(events[i].getKind() == DebugEvent.CREATE) {
            		//System.out.println("come to event CREATE");
            	}
            	else if (events[i].getKind() == DebugEvent.TERMINATE) {
                	//System.out.println("DebugEvent: TERMINATE " + (events[i].getSource()).toString());
    				
                    try {
                        running = false;
                        
                        // Fix the bug that streamsProxy not closed before
                		if (streamsProxy instanceof StreamsProxy) {
                			((StreamsProxy)streamsProxy).close();
                		}
                		
                        String content = streamsProxy.getRealOutputStreamMonitor().getContents();
                        System.out.println(content) ;
                        TraceParser parser = new TraceParser();
                        
                        if ( sourceFolder != null ){
                        	parser.setSourceFolder(sourceFolder);
                        }
                        
                        result = parser.parse(content);
                        
                        if (result == Result.FAILURE) {
                        	 //System.out.println("come to event FAILURE");
                            // pathetic way of updating view... but it works...
                            // ILaunch l = new DummyLaunch(launch.getLaunchConfiguration());
                            // l.addProcess(process);
                            // new VerifyDebugTarget(config, l, process, trace, name);
                            // DebugPlugin.getDefault().getLaunchManager().addLaunch(l);
                            // DebugPlugin.getDefault().getLaunchManager().removeLaunch(l);

                            // LaunchManager manager = (LaunchManager)DebugPlugin.getDefault().getLaunchManager();
                            // manager.fireUpdate(launch, LaunchManager.CHANGED);

                            String name = "[" + config.verifier.getName().toUpperCase() + "] (claim: " + claim.id + ")";
                            trace = parser.getTrace();
                            VerifyThread verifyThread = new VerifyThread(config, launch, trace, root, name);
                            showClaimsRunner.getTarget().addThread(verifyThread);
                            launch.setSourceLocator(new VerifySourceLocator());

                            // DebugPlugin.getDefault().getLaunchManager().addLaunch(new TakeTakeCrap(new TakeMoreCrap(launch, "metoo")));

                            DebugPlugin manager = DebugPlugin.getDefault();
                            if (manager != null) {
                                manager.fireDebugEventSet(new DebugEvent[] { new DebugEvent(verifyThread, DebugEvent.CREATE) });
                            }
                        }

                        // fireChangeEvent();
                    } catch (CoreException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } 
                    /*catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} */
                    finally {
                        DebugPlugin.getDefault().removeDebugEventListener(this);

                        // refresh claims view...
                        ClaimsView.refreshClaim(claim);
                    }
                    return;
                }
            }
        }

        /**
         * Fires the given debug event.
         * 
         * @param event
         *            debug event to fire
         */
        protected void fireEvent(DebugEvent event) {
            DebugPlugin manager = DebugPlugin.getDefault();
            if (manager != null) {
                manager.fireDebugEventSet(new DebugEvent[] { event });
            }
        }

        /**
         * Fires a change event.
         */
        protected void fireChangeEvent() {
            fireEvent(new DebugEvent(this, DebugEvent.CHANGE));
        }

    }

    private String getConsoleName() {
        return "[" + config.verifier.getName().toUpperCase() + "] (claim: " + claim.id + ")";
    }

    public String[] getCommand() {
    	LinkedList<String> cmdArgs = new LinkedList<String>();
    	
//    	cmdArgs.add(getVerifierExecutable());
    	cmdArgs.add("cbmc");
    	cmdArgs.add("--claim");
    	cmdArgs.add(claim.id);
    	cmdArgs.add("--xml-ui");
    	cmdArgs.add("--no-unwinding-assertions");
    	cmdArgs.add("--unwind");
    	cmdArgs.add(""+unwind);
//    	cmdArgs.add("--div-by-zero-check");
//    	cmdArgs.add("--pointer-check");
//    	cmdArgs.add("--overflow-check");
    	cmdArgs.add(gotoCCprogram);
    	
    	StringBuffer sb = new StringBuffer();
    	for ( String arg : cmdArgs ){
    		sb.append(arg);
    		sb.append(" ");
    	}
    	System.out.println(sb.toString());
    	
		return cmdArgs.toArray(new String[cmdArgs.size()]);
    }

    public int getUnwind() {
		return unwind;
	}

	public void setUnwind(int unwind) {
		this.unwind = unwind;
	}

	private LinkedList<String> packBounds() {
        LinkedList<String> params = new LinkedList<String>();
        
        if (config.verifier == Verifier.CBMC ) {
            params.add("--unwind");
            params.add(Integer.toString(config.cbmc_unwind));
            
            boolean first = true;
            for (Loop loop : showClaimsRunner.getLoopsRun().getLoops()) {
                if (loop.bound == -1)
                    continue;
                
                if ( first ) {
                    params.add("--unwindset");
                    first = false;
                }
                else {
                    params.add(",");
                }
                params.add(loop.id + ":" + loop.bound);
            }
        }
        if (config.verifier == Verifier.HW_CBMC || config.verifier == Verifier.EBMC) {
            params.add("--bound");
            params.add(String.valueOf(config.hwcbmc_bound));
        }
        if (config.verifier == Verifier.SATABS) {
            params.add("--iterations");
            params.add(String.valueOf(config.satabs_iterations));
        }
        
        return params;
    }

    public void resume() {
        // TODO Auto-generated method stub

    }

    public void stop() {
        // TODO Auto-generated method stub

    }

    public boolean isRunning() {
        return running;
    }

    /**
     * @return the trace
     */
    public Trace getTrace() {
        return trace;
    }

	public void setSourceFolder(String folderPath) {
		sourceFolder = folderPath;
	}

}
