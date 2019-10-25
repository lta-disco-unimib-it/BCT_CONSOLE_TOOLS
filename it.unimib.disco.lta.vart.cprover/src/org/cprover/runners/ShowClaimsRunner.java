package org.cprover.runners;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.cprover.communication.Claim;
import org.cprover.communication.Location;
import org.cprover.communication.Message;
import org.cprover.communication.Result;
import org.cprover.communication.Tag;
import org.cprover.core.CProverPlugin;
import org.cprover.core.StreamsProxy;
import org.cprover.launch.VerifyConfig;
import org.cprover.model.CProverProcess;
import org.cprover.model.VerifyDebugTarget;
import org.cprover.views.ClaimsView;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener;

/**
 * Runs Satabs and gets all claims. 
 * 
 * @author Gérard Basler
 */
public class ShowClaimsRunner extends AbstractRunner {
    
    private VerifyDebugTarget target;
    
	/**
	 * List of claims to check or already checked.
	 */
	public LinkedList<Claim> claims;

	/**
	 * Root directory of project. Used to cut filenames.
	 */
	public String root;

    /**
     * We might need bounds for the loops ( CBMC ) 
     */
    private LoopsRunner loopsRun;

	private String sourceFolder = "";

	private String gotoCCprogram;

	
	
	public void setGotoCCprogram(String gotoCCprogram) {
		this.gotoCCprogram = gotoCCprogram;
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public ShowClaimsRunner(VerifyConfig config, ILaunch launch) {
		super(config, launch);
	}
	
	/**
     * @return the target
     */
    public VerifyDebugTarget getTarget() {
        return target;
    }

    public void run(IProgressMonitor monitor) throws CoreException {
		this.claims = new LinkedList<Claim>();
		this.root = config.workingDirectory.toString();
		
		createWorkingDir();
		
		if ( gotoCCprogram == null ){
			return;
		}
		
        IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 3);
        subMonitor.beginTask("Getting claims", 1);
		
        // TODO: check for exe, open config if not available (see doxygen plugin)

        Process p = DebugPlugin.exec(getCommand(), null/*config.workingDirectory*/, null /*config.envp*/);
        CProverProcess process = (CProverProcess) DebugPlugin.newProcess(launch, p, "Getting claims from " +config.verifier);
        //target = new VerifyDebugTarget(config, launch, process, "Counterexamples Found", root);
        target = new VerifyDebugTarget(config, launch, process, "Traces", root);
        
        subMonitor.worked(1);
        
        try {
        	// wait for SATABS to finish
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
//			Utils.showErrorDialog(CProverPlugin.getActiveWorkbenchShell(), "", e.toString(), Utils.NORMAL);
//			CProverPlugin.logWithException("Error parsing result");
			throw new CoreException(CProverPlugin.makeStatus(IStatus.ERROR, e.getCause().getMessage(), null));
		}
		
//		Utils.showInformationDialog(CProverPlugin.getActiveWorkbenchShell(), "", "len: " + result.length(), Utils.NORMAL);

		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(new ILaunchesListener() {
			public void launchesAdded(ILaunch[] launches) {
			}

			public void launchesChanged(ILaunch[] launches) {
			}

			public void launchesRemoved(ILaunch[] launches) {
			    for (ILaunch l : launches) {
                    if( l == launch ) {
                        ClaimsView.removeClaimsRun(ShowClaimsRunner.this);
                    }
                }
			}
		});
		
		subMonitor.done();
	}
	
    private void createWorkingDir() {
//        IProject project = taskFile.getProject();
//        String resDir = "Results";
//        String tskDir = taskFile.getName().substring(0, taskFile.getName().indexOf('.'));
//        String prgDir = verifier;
//        String clDir = "claims";
//        
//        if (!project.getFolder(resDir).exists()) {
//            try {
//                project.getFolder(resDir).create(true, true, null);
//            } catch (CoreException e) {
//                CProverPlugin.showError(e);
//            }
//        }
//        if (!project.getFolder(resDir).getFolder(tskDir).exists()) {
//            try {
//                project.getFolder(resDir).getFolder(tskDir).create(true, true, null);
//            } catch (CoreException e) {
//                CProverPlugin.showError(e);
//            }
//        }
//        if (!project.getFolder(resDir).getFolder(tskDir).getFolder(prgDir).exists()) {
//            try {
//                project.getFolder(resDir).getFolder(tskDir).getFolder(prgDir).create(true, true, null);
//            } catch (CoreException e) {
//                CProverPlugin.showError(e);
//            }
//        }
//        if (!project.getFolder(resDir).getFolder(tskDir).getFolder(prgDir).getFolder(clDir).exists()) {
//            try {
//                project.getFolder(resDir).getFolder(tskDir).getFolder(prgDir).getFolder(clDir).create(true, true, null);
//            } catch (CoreException e) {
//                CProverPlugin.showError(e);
//            }
//        }
    }

    public String[] getCommand() {
    	LinkedList<String> cmdArgs = new LinkedList<String>();
    	
//    	cmdArgs.add(getVerifierExecutable());
    	cmdArgs.add("cbmc");
    	cmdArgs.add("--show-claims");
    	cmdArgs.add("--xml-ui");
    	cmdArgs.add("--no-unwinding-assertions");
//    	cmdArgs.add("--div-by-zero-check");
//    	cmdArgs.add("--pointer-check");
//    	cmdArgs.add("--overflow-check");
    	cmdArgs.add(gotoCCprogram);
    	
    	System.out.println("EXECUTING: "+cmdArgs);
    	
		return cmdArgs.toArray(new String[cmdArgs.size()]);
    }

    protected void parseResult(InputStream inputStream) throws IOException {
		List<String> list = convertStreamToList(inputStream);
		parseResult(list);		
	}
    
    protected void parseResult(String content) throws IOException {
        List<String> list = convertStringToList(content);
        parseResult(list);		
    }

	protected void parseResult(List<String> content) {
		String crtParent = null;
		Message crtMsg = null;
		Location crtLoc = new Location();;
		Claim crtClaim = null;
		String crtLocItem = null;
		
		boolean CBMC_5 = false;
		
		
		result = Result.SUCCESS;
		
		for (String line: content) {
			System.out.println(line);
		}
		
		for (String line: content) {
			System.out.println(line);
			Tag t = parseTag(line);
			if (t == null){
				crtLoc = new Location();
				continue;
			}
			
			
			if (t.name.equals("location")) {
				
				if ( CBMC_5 ){
					if (crtParent.equals("claim")){
						
						crtLoc = new Location();
						crtClaim.location = crtLoc;
						{
							String value = t.getAttribute("file");
							crtLoc.file = sourceFolder+value;
						}

						{
							String value = t.getAttribute("line");
							crtLoc.line = value.equals("")? 0: Integer.parseInt(value);
						}
						
						//crtLoc.column = t.value.equals("")? 0: Integer.parseInt(t.value);
					}
						
				} else {
				
					crtLocItem = t.name;

					if (crtParent.equals("message"))
						crtMsg.location = crtLoc;
					if (crtParent.equals("claim"))
						crtClaim.location = crtLoc;
				}
			}
			else if (t.name.equals("program")) {
				if ( t.value.contains("CBMC 5") ){
					CBMC_5=true;
				}
			}
			else if (t.name.equals("message")) {
				crtMsg = new Message(t.type);
				if (t.type.equals("ERROR"))
					this.result = Result.ERROR;
				this.messages.add(crtMsg);
				crtParent = t.name;
			}
			else if ( (CBMC_5==false && t.name.equals("claim")) ) {
				crtClaim = newClaim();
				crtParent = t.name;
			}
			else if (t.name.equals("file")) {
				crtLoc.file = sourceFolder+t.value;
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
			else if (t.name.equals("number")) {
				crtClaim.id = t.value;
			}
			else if (t.name.equals("description")) {
				crtClaim.description = t.value;
			}
			else if (t.name.equals("property")) {
				if ( CBMC_5 == true ){
					if ( "assertion".equals(t.getAttribute("class"))){
						crtClaim = newClaim();
						crtClaim.id = t.getAttribute("name");
						crtParent = "claim";
					}
//					<property class="assertion" name="is_available.assertion.3">
//					  <location file="src/store.c" function="is_available" line="28"/>
//					  <description>assertion prod != ((struct product *)NULL)</description>
//					  <expression>prod != ((struct product *)NULL)</expression>
//					</property>
					
					
				} else {
					crtClaim.property = t.value;
				}
			}
			else if (t.name.equals("expression")) {
				crtClaim.expression = t.value;
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

	private Claim newClaim() {
		Claim crtClaim;
		crtClaim = new Claim();
		crtClaim.clRun = new ClaimCheckRunner(config, launch, crtClaim, this);
		crtClaim.clRun.showClaimsRunner = this;
			
		this.claims.add(crtClaim);
		return crtClaim;
	}

    public void setLoopsRun(LoopsRunner loopsRun) {
        this.loopsRun = loopsRun;
    }

    public LoopsRunner getLoopsRun() {
        return loopsRun;
    }
}
