package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ResourcesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.JavaResourcesUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.hyades.probekit.editor.internal.core.util.ProbekitMessages;
import org.eclipse.hyades.probekit.editor.internal.ui.CompoundLabelProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.CompoundTreeContentProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.DialogUtil;
import org.eclipse.hyades.probekit.editor.internal.ui.JavaElementContentProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.JavaElementLabelProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.ResourceContentProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.ResourceLabelProvider;
import org.eclipse.hyades.probekit.editor.internal.ui.SelectAnythingValidator;
import org.eclipse.hyades.probekit.ui.internal.ContextIds;
import org.eclipse.hyades.probekit.ui.internal.ProbekitUIPlugin;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;

public abstract class InstrumentWithProbe  implements IObjectActionDelegate  {

	public InstrumentWithProbe() {
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

	public void run(IAction action) {
		
		final ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		
		
		if ( selection instanceof IStructuredSelection ){
			
			final Display display = PlatformUI.getWorkbench().getDisplay();
			final Shell shell = display.getActiveShell();
			
			
			
			
			
			IStructuredSelection ss = (IStructuredSelection) selection;
			IFile selectedElement = (IFile) ss.getFirstElement();
			File selectedFile = selectedElement.getLocation().toFile();
			try {
			final MonitoringConfiguration mc = MonitoringConfigurationDeserializer.deserialize(selectedFile);
			
			doAdditionalActions(mc);
			
			
			MonitoringConfigurationSerializer.serialize(selectedFile, mc);
			
			IRunnableWithProgress runnable = new IRunnableWithProgress(){

				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					try {
						ConfigurationFilesManager.updateConfigurationFiles(mc);
						
					} catch (ConfigurationFilesManagerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
				}
				
			};
			
			NullProgressMonitor monitor = new NullProgressMonitor();
			
			runnable.run(monitor);
			
			
			List resources = getChosenResourcesProbekit(shell,mc);
			if ( resources == null ){
				return;
			}
			
			
			
			
			
			//Create and array with  resources to instrument and build th elist of the interested projects
			final String resourcesToInstrument[] = new String[resources.size()];
			HashSet<IProject> projectsToInstrument = new HashSet<IProject>();
			for ( int i = 0; i< resources.size(); ++i ){
				Object resource = resources.get(i);
				
				if ( resource instanceof IFile ){
					resourcesToInstrument[i]=((IFile)resource).getLocation().toString();
					projectsToInstrument.add(((IFile)resource).getProject());
				} else if ( resource instanceof IPackageFragmentRoot ){
					resourcesToInstrument[i]=((IPackageFragmentRoot)resource).getPath().toOSString();
					projectsToInstrument.add(((IPackageFragmentRoot)resource).getJavaProject().getProject());
				} else if ( resource instanceof String ){
					resourcesToInstrument[i]=(String)resource;
					IFile selected = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path((String)resource));
					if ( selected != null ){
						projectsToInstrument.add(selected.getProject());
					}
				}
				
			}
			
			
			boolean result = MessageDialog.openQuestion(shell, "Update configuration", "Do you want to update your launch configurations?" +
			"\nIf you press YES the launch configurations of the projects you are going to instrument will be modified:" +
			"\n BCT_HOME will be properly set \nbct classes will be added to your library path");

			
			
			InstrumentWithProgress runInstrument = getInstrumentWithProgress(
					shell,
					display,
					selectedElement,
					resourcesToInstrument,
					projectsToInstrument,
					mc,
					result); 
				
			//sSystem.out.println("HERE");
			ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(shell);
			
				progressMonitorDialog.run(true, true, runInstrument);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MessageDialog.openError(shell, "Instrumentation problem", "Problem while instrumenting the project "+e.getMessage());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MessageDialog.openError(shell, "Instrumentation problem", "Problem while instrumenting the project "+e.getMessage());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MessageDialog.openError(shell, "Instrumentation problem", "Problem while instrumenting the project "+e.getMessage());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MessageDialog.openError(shell, "Instrumentation problem", "Problem while instrumenting the project "+e.getMessage());
			} catch (DefaultOptionsManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MessageDialog.openError(shell, "Instrumentation problem", "Problem while instrumenting the project "+e.getMessage());
			}
		}

	}

	



	protected abstract void doAdditionalActions(MonitoringConfiguration mc) throws CoreException, DefaultOptionsManagerException;

	protected abstract InstrumentProbeWithProgress getInstrumentWithProgress(
			Shell shell, Display display, IFile selectedElement, 
			String[] resourcesToInstrument, Set<IProject> projectsToInstrument, 
			MonitoringConfiguration mc, boolean updateLaunchConfiguration);
	
	public void selectionChanged(IAction action, ISelection selection) {
	}

	private List getChosenResourcesProbekit(Shell shell, MonitoringConfiguration mc) {
		try {
//			IContainer outputContainer = JavaUtil.getOutputContainer(probeFile);
//			if(outputContainer == null) {
//				// Selected the .probe file in the bin folder by mistake?
//				MessageDialog.openError(shell, NLS.bind(ProbekitMessages._90,
//						new Object[] { probeFile.getName() }),
//						ProbekitMessages._89);
//				return Collections.EMPTY_LIST;
//			}
			
			//IClassFile[] generatedClassFiles = JavaUtil.getClassFiles(probeFile);
			
			IPackageFragmentRoot[] allArchives = JavaResourcesUtil.getAllArchivesAsArray();
			IPackageFragmentRoot[] archives = JavaResourcesUtil.getExternalArchives(allArchives);
			//IFile[] classesAndArchives = ProbeInstrumenterActionUtil.getBinaryFilesExcluding(allArchives, generatedClassFiles);
			IFile[] classesAndArchives = JavaResourcesUtil.getBinaryFilesExcluding(allArchives, new IClassFile[0]);
			List binary = new ArrayList(archives.length + classesAndArchives.length);
			Set temp = new HashSet(archives.length + classesAndArchives.length);
			for(int i=0; i<archives.length; i++) {
				IPackageFragmentRoot archive = archives[i];
				
				binary.add(archive);
				
				IJavaProject jp = archive.getJavaProject();
				if((jp != null) && (jp.exists())) {
					temp.add(jp);
				}
			}
			for(int i=0; i<classesAndArchives.length; i++) {
				IFile file = classesAndArchives[i];
				
				binary.add(file);
				
				IJavaProject jp = JavaCore.create(file.getProject());
				if((jp != null) && (jp.exists())) {
					temp.add(jp);
				}
			}			
			
			final IJavaProject[] javaProjects = (IJavaProject[])temp.toArray(new IJavaProject[0]);
			final CompoundLabelProvider labelProvider = 
				new CompoundLabelProvider(
						new ResourceLabelProvider(),
						new JavaElementLabelProvider());

			final CompoundTreeContentProvider treeContentProvider = 
				new CompoundTreeContentProvider(
						new ResourceContentProvider(classesAndArchives),
						new JavaElementContentProvider(archives));
			
			//FileDialog d = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
			
	    	final CheckedTreeSelectionDialog dialog = 
	    		DialogUtil.createCompoundDialog(
		    		shell,
					javaProjects,
					treeContentProvider,
					labelProvider
				);
	    	
	    	
	    	//now select referred project binaries
	    	ArrayList<Object> selectedList = new ArrayList<Object>();
	    	String refPrj = mc.getReferredProjectName();
	    	if ( refPrj != null && refPrj.length() > 0 ){
	    		
	    		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(refPrj);
	    		
	    		
	    		ResourcesMonitoringOptions resourcesMonitoringOptions = mc.getResourcesMonitoringOptions();
	    		for( Object bin : binary){
	    			
	    			if ( bin instanceof IFile  ){
	    				
	    				IFile file = (IFile)bin;
	    				
	    				if ( resourcesMonitoringOptions.isResourceMonitored(file.getProjectRelativePath()) ){
	    					selectedList.add(file);
	    				}
	    				
	    			} else if ( bin instanceof IPackageFragmentRoot ){
	    				IPackageFragmentRoot fr = (IPackageFragmentRoot)bin;
	    				
	    				if ( resourcesMonitoringOptions.isResourceMonitored(fr) ) {
	    					selectedList.add(fr);
	    				}
	    			}
	    		}
	    		
	    		Object selectedElements[] = selectedList.toArray(new Object[0]);
	    		
	    		
		    	dialog.setInitialSelections(selectedElements);
	    	
		    	
		    	//Expand the project whose elements that have been automatically selected
		    	List<IJavaProject> toExpand = new ArrayList<IJavaProject>();
		    	
		    	for ( IJavaProject jp : javaProjects){
		    		
		    		if( jp.getProject().equals(project)){
		    			toExpand.add(jp);
		    		}
		    		
		    	}
		    	
		    	Object expandedElements[] = toExpand.toArray( new Object[toExpand.size()] );
		    	dialog.setExpandedElements(expandedElements);
    			
		    	
	    	}
	    	
	    	
	    	
	    	
			dialog.setValidator(new SelectAnythingValidator(binary));
			dialog.setTitle("Title");
			dialog.setMessage(ProbekitMessages._95);
			
			dialog.create(); // create dialog to get its shell
			
			Shell dialogShell = dialog.getShell();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(dialogShell, ContextIds.PROBEKIT_INSTRUMENT);
			
			dialog.open();
			
			Object[] selected = dialog.getResult();
			if(selected != null) {
				List chosen = treeContentProvider.getClassAndJarElements(selected);
				return chosen;
			}
			
			return null;
		}
		catch(CoreException exc) {
    		ProbekitUIPlugin.getPlugin().log(exc);
    		MessageDialog.openError(
    				shell, 
    				NLS.bind(ProbekitMessages._90, new Object[]{""}),
					ProbekitMessages._91
					);
		}

		return Collections.EMPTY_LIST;
    }



}
