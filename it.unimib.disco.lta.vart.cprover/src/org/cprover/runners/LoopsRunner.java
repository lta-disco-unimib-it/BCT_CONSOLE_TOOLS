package org.cprover.runners;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.cprover.communication.Location;
import org.cprover.communication.Loop;
import org.cprover.communication.Message;
import org.cprover.communication.Result;
import org.cprover.communication.Tag;
import org.cprover.core.CProverPlugin;
import org.cprover.core.StreamsProxy;
import org.cprover.launch.VerifyConfig;
import org.cprover.model.CProverProcess;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener;

/**
 * Gets loop information from CBMC.
 * 
 * @author Gérard Basler
 */
public class LoopsRunner extends AbstractRunner {
    
    private List<Loop> loops;
    
    /**
     * Root directory of project. Used to cut filenames.
     */
    public String root;
    
    /**
     * Needed to get results of claim check back to loops view such that a loop bounds violation can be shown.
     */
    private ShowClaimsRunner showClaimsRunner;
    
	/**
     * @param config
	 * @param launch
     */
    public LoopsRunner(VerifyConfig config, ILaunch launch, ShowClaimsRunner showClaimsRunner) {
        super(config, launch);
        this.showClaimsRunner = showClaimsRunner;
        showClaimsRunner.setLoopsRun(this);
        this.root = config.workingDirectory.toString();
    }
	
    public ShowClaimsRunner getShowClaimsRunner() {
        return showClaimsRunner;
    }

    public void run(IProgressMonitor monitor) throws CoreException {
        this.loops = new LinkedList<Loop>();
        
        IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 3);
        subMonitor.beginTask("Getting loops", 1);
        
        // TODO: check for exe, open config if not available (see doxygen plugin)

        Process p = DebugPlugin.exec(getCommand(), null/*config.workingDirectory*/, null /*config.envp*/);
        CProverProcess process = (CProverProcess) DebugPlugin.newProcess(launch, p, "Getting loops from " +config.verifier);
        
        subMonitor.worked(1);
        
        try {
            // wait for CBMC to finish
            p.waitFor();
        } catch (InterruptedException e) {
            CProverPlugin.logWithException("Error running executable");
        }
        
        subMonitor.worked(1);
        
        // wait for stream processing to finish
        process.close();
        
        StreamsProxy streamsProxy = (StreamsProxy) process.getStreamsProxy();
        String content = streamsProxy.getRealOutputStreamMonitor().getContents();
        try {
            parseResult(content);
        } catch (IOException e) {
//          Utils.showErrorDialog(CProverPlugin.getActiveWorkbenchShell(), "", e.toString(), Utils.NORMAL);
//          CProverPlugin.logWithException("Error parsing result");
            throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, e.getCause().getMessage(), null));
        }
        
//      Utils.showInformationDialog(CProverPlugin.getActiveWorkbenchShell(), "", "len: " + result.length(), Utils.NORMAL);

        DebugPlugin.getDefault().getLaunchManager().addLaunchListener(new ILaunchesListener() {
            public void launchesAdded(ILaunch[] launches) {
            }

            public void launchesChanged(ILaunch[] launches) {
            }

            public void launchesRemoved(ILaunch[] launches) {
                for (ILaunch l : launches) {
                    if( l == launch ) {
//                        ClaimsView.removeClaimsRun(ShowClaimsRunner.this);
                    }
                }
            }
        });
        
        subMonitor.done();
    }
    
    public String[] getCommand() {
        LinkedList<String> cmdArgs = new LinkedList<String>();
        
        cmdArgs.add(getVerifierExecutable());
        
        cmdArgs.add("--show-loops");
        cmdArgs.add("--xml-ui");
        cmdArgs.addAll(config.files);
        return cmdArgs.toArray(new String[cmdArgs.size()]);
    }
    
    protected void parseResult(String content) throws IOException {
        List<String> list = convertStringToList(content);
        parseResult(list);      
    }
    
    public void parseResult(List<String> content) {
		this.reset();
		
		String crtParent = null;
		Message crtMsg = null;
		Location crtLoc = new Location();
		Loop crtLoop = null;
		String crtLocItem = null;
		
		this.result = Result.SUCCESS;
		
		for (String line: content) {
			Tag t = this.parseTag(line);
			if (t == null){
				crtLoc = new Location();
				continue;
			}
			
			if (t.name.equals("location")) {
				crtLocItem = t.name;
				if (crtParent.equals("message"))
					crtMsg.location = crtLoc;
				if (crtParent.equals("loop"))
					crtLoop.location = crtLoc;
			}
			else if (t.name.equals("message")) {
				crtMsg = new Message(t.type);
				if (t.type.equals("ERROR"))
					this.result = Result.ERROR;
				this.messages.add(crtMsg);
				crtParent = t.name;
			}
			else if (t.name.equals("loop")) {
				crtLoop = new Loop();
				this.loops.add(crtLoop);
				crtParent = t.name;
			}
			else if (t.name.equals("file")) {
				crtLoc.file = t.value;
			}
			else if (t.name.equals("line")) {
				crtLoc.line = t.value.equals("")? 0: Integer.parseInt(t.value);
			}
			else if (t.name.equals("column")) {
				crtLoc.column = t.value.equals("")? 0: Integer.parseInt(t.value);
			}
			else if (t.name.equals("text")) {
				crtMsg.text = t.value;
			}
			else if (t.name.equals("loop-id")) {
				crtLoop.id = t.value;
			}
			else if (t.name.equals("named_sub")) {
				crtLocItem = t.type;
			}
			else if (t.name.equals("id") && crtLocItem != null) {
				if (crtLocItem.equals("file")) {
//					if (task.useXmlBin)
//						crtLoc.file = task.root+ CProverPlugin.getSeparator()+ t.value;
//					else
						crtLoc.file = t.value;
				}
				if (crtLocItem.equals("line"))
					crtLoc.line = t.value.equals("")? 0: Integer.parseInt(t.value);
				if (crtLocItem.equals("column"))
					crtLoc.column = t.value.equals("")? 0: Integer.parseInt(t.value);
			}
		}		
	}

	/**
     * @return the loops
     */
    public List<Loop> getLoops() {
        return loops;
    }

    public void reset() {
		this.loops.clear();
	}
	
	public Loop getLoop(String id) {
		for (Loop l: this.loops) {
			if (l.id.equals(id))
				return l;
		}
		return null;
	}
}
