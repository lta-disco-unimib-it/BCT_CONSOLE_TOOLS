/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.editors;


import it.unimib.disco.lta.bct.bctjavaeclipse.core.ava.AvaResultsSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfigurationFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.AvaAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ViolationsAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.AnalysisResultListener;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.RegressionAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization.BctViolationsAnalysisDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.serialization.BctViolationsAnalysisSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.AvaAnalysisResultComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.BctLogAnalysisComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.RegressionAnalysisResultsComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.ViolationsAnalysisResultComposite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.ChangeEvent;
import org.eclipse.draw2d.ChangeListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.MultiPageEditorPart;


public class BctViolationsLogAnalysisEditor extends MultiPageEditorPart implements IResourceChangeListener, BCTObserver {

	private IFile sourceFile;

	private boolean dirty;

	private BctLogAnalysisComposite analysisComposite;

	private ViolationsAnalysisResultComposite resultComposite;

	private BctViolationsAnalysisConfiguration violationsAnalysisConfig;

	private IEditorSite site;

	private AvaAnalysisResultComposite avaComposite;

	private boolean trackDirtyChanges = false;

	private RegressionAnalysisResultsComposite regressionComposite;

	private MonitoringConfiguration monitoringConfiguration;

	private int regressionAnalysisPageIndex;

	private boolean showActions;	

	/**
	 * Creates a multi-page editor example.
	 */
	public BctViolationsLogAnalysisEditor() {
		super();

		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	private static GridData createFill()
	{
		GridData gd = new GridData();
		gd.horizontalAlignment = 4;
		gd.grabExcessHorizontalSpace = true;
		gd.verticalAlignment = 4;
		gd.grabExcessVerticalSpace = true;
		return gd;
	}


	/**
	 * Creates page 0 of the multi-page editor,
	 * which contains a text editor.
	 */
	void createAnalysisTab() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);

		showActions = true;

		if ( monitoringConfiguration != null && monitoringConfiguration.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
			showActions = false;
		}

		analysisComposite = new BctLogAnalysisComposite(composite, SWT.NONE, showActions );


		composite.setLayoutData(createFill());

		int index = addPage(composite);
		setPageText(index, "Analysis setup");

		composite.setContent(analysisComposite);	
		composite.setMinSize(analysisComposite.computeSize(SWT.DEFAULT,SWT.DEFAULT));

	}




	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createAnalysisTab();
		createResultsTabs();

		loadConfiguration(violationsAnalysisConfig);

		analysisComposite.addBCTObserver(this);

		if ( showActions ){
			resultComposite.addBCTObserver(this);

			//		analysisComposite.setSite(site);

			avaComposite.addBCTObserver(this);

			analysisComposite.addFailureToAnalyzeChangeListener(new ChangeListener(){

				public void handleStateChanged(ChangeEvent event) {
					resultComposite.removeAll();
					avaComposite.clear();
				}

			});
		}

		regressionComposite.setSite(site);


		selectActivePage();

	}

	private void selectActivePage() {
		if ( this.monitoringConfiguration == null ){
			return;
		}
		if ( this.monitoringConfiguration.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config &&  this.violationsAnalysisConfig.getFilteringResult() != null ){
			setActivePage(regressionAnalysisPageIndex);
		}
	}

	private void setDirty(boolean isDirty) {
		//		try {
		//			throw new Exception();
		//		} catch (Exception e) {
		//			System.err.println("EDITOR DIRTY "+trackDirtyChanges);
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		if ( isDirty && ! trackDirtyChanges ){
			return;
		}
		if ( dirty != isDirty ){
			dirty = isDirty;
			this.firePropertyChange(PROP_DIRTY);
		}
	}

	private void loadConfiguration(
			BctViolationsAnalysisConfiguration violationsAnalysisConfig2) {
		analysisComposite.load(violationsAnalysisConfig2);





		ViolationsAnalysisResult vaResult = violationsAnalysisConfig2.getViolationsAnalysisResult();

		if ( showActions ){
			resultComposite.load(analysisComposite.getMonitoringConfiguration(),vaResult,analysisComposite.getViolations());

			File avaResultFile = getAvaAnalysisFile(violationsAnalysisConfig2);
			try {
				if ( avaResultFile.exists() ){
					AvaAnalysisResult avaResult = AvaResultsSerializer.load(avaResultFile);
					if ( avaResult != null ){
						avaComposite.load(avaResult);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.getInstance().log(e);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.getInstance().log(e);
			}
		}

		regressionComposite.setMonitoringConfiguration(analysisComposite.getMonitoringConfiguration());
		regressionComposite.load(violationsAnalysisConfig2.getFilteringResult());


		trackDirtyChanges = true;
		//violationsAnalysisConfig2.getA

		selectActivePage();
	}

	private void createResultsTabs() {
		ScrolledComposite composite;
		int index;

		if ( showActions ){
			composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
			composite.setExpandHorizontal(true);
			composite.setExpandVertical(true);


			resultComposite=new ViolationsAnalysisResultComposite(composite,SWT.NONE);
			resultComposite.setBounds(15, 15, 575, 550);


			composite.setContent(resultComposite);	
			composite.setMinSize(resultComposite.computeSize(SWT.DEFAULT,SWT.DEFAULT));



			index = addPage(composite);
			setPageText(index, "Analysis results");

			analysisComposite.addViolationsAnalysisListener(new AnalysisResultListener<ViolationsAnalysisResult>(){

				public void analysisFinished(ViolationsAnalysisResult result) {
					resultComposite.load(analysisComposite.getMonitoringConfiguration(),result,analysisComposite.getViolations());
				}

				public void analysisStartup() {
					resultComposite.removeAll();
				}

			});

			composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
			composite.setExpandHorizontal(true);
			composite.setExpandVertical(true);

			avaComposite = new AvaAnalysisResultComposite(composite,SWT.NONE);
			avaComposite.setBounds(15, 15, 575, 550);

			composite.setContent(avaComposite);	
			composite.setMinSize(avaComposite.computeSize(SWT.DEFAULT,SWT.DEFAULT));

			index = addPage(composite);
			setPageText(index, "AVA Analysis results");

			analysisComposite.addAvaAnalysisListener(new AnalysisResultListener<AvaAnalysisResult>(){

				public void analysisFinished(AvaAnalysisResult result) {
					avaComposite.load(result);
					setDirty(true);
				}

				public void analysisStartup() {

				}

			});
		}

		composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);

		regressionComposite = new RegressionAnalysisResultsComposite(composite, SWT.NONE);
		//		regressionComposite.setBounds(15, 15, 575, 550);
		//		regressionComposite.setSize(400, 400);
		composite.setContent(regressionComposite);	
		composite.setMinSize(regressionComposite.computeSize(SWT.DEFAULT,SWT.DEFAULT));

		index = addPage(composite);
		regressionAnalysisPageIndex = index;
		setPageText(index, "Regression Analysis Results");

		final int regressionPage = index;
		analysisComposite.addRegressionAnalysisListener(new AnalysisResultListener<RegressionAnalysisResult>(){

			public void analysisFinished(RegressionAnalysisResult result) {
				regressionComposite.load( result );

				setActivePage(regressionPage);
				//				setDirty(true); FIXME: uncomment this when we will save the regression analysis
			}

			public void analysisStartup() {

			}

		});
	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this 
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		if ( sourceFile == null ){
			doSaveAs();
			return;
		}
		save(sourceFile);
	}

	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {

		SaveAsDialog dialog= new SaveAsDialog(null);
		dialog.create();

		if (dialog.open() == Window.CANCEL) {
			return;
		}

		IPath filePath= dialog.getResult();
		if (filePath == null) {
			return;
		}

		IWorkspace workspace= ResourcesPlugin.getWorkspace();
		IFile file= workspace.getRoot().getFile(filePath);

		save(file);

	}


	private void save(IFile file) {
		try{



			BctViolationsAnalysisConfiguration conf = getConfiguration();

			//			if ( analysisComposite.getLogDataChanged() ){ //SAVE LOG
			//				List<File> logs = conf.retrieveLogFiles();
			//				File log;
			////				if ( logs.size() > 1 ){
			//				String path = conf.getAnomaliesGraphsPath();
			//				File folder = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path)).getLocation().toFile();
			//					
			//				log = new File( folder, "newLog" );
			////				} else {
			////					log = logs.get(0);
			////				}
			//				log.delete();
			//				
			//				
			//				CBEViolationsRecorder vr = new FileCBEViolationsRecorder(log);
			//				for ( Failure f : analysisComposite.getFailures() ){
			//					vr.recordFailure(f);	
			//				}
			//				
			//				
			//				for ( BctModelViolation v  : analysisComposite.getViolations() ){
			//					vr.recordViolation(v);	
			//				}
			//				
			//				for ( BctAnomalousCallSequence v  : analysisComposite.getAnomalousCallSequences() ){
			//					vr.recordAnomalousCallSequence(v);	
			//				}
			//				
			//				logs = new ArrayList<File>();
			//				logs.add(log);
			//				
			//				conf.defineLogFiles(logs);
			//			}

			BctViolationsAnalysisSerializer.serialize(file.getLocation().toFile(), conf );

			if ( showActions ){
				File f = getAvaAnalysisFile(conf);
				AvaResultsSerializer.serialize(avaComposite.getAvaAnalysisResult(), f);
			}

			setDirty( false );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			MessageDialog.openError(null, "Error saving", "An error occurred while saving the configuration "+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			MessageDialog.openError(null, "Error saving", "An error occurred while saving the configuration "+e.getMessage());
			//		} catch (RecorderException e) {
			//			// TODO Auto-generated catch block
			//			e.printStackTrace();
			//			MessageDialog.openError(null, "Error saving", "An error occurred while saving the configuration "+e.getMessage());
		}
	}


	private File getAvaAnalysisFile(BctViolationsAnalysisConfiguration conf) {
		String anomaliesPath = conf.getAnomaliesGraphsPath();
		if ( anomaliesPath == null ){
			return null;
		}
		IPath destFolderPath = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(anomaliesPath)).getLocation();
		File destFolder;
		if ( destFolderPath == null ){
			//path is absolute
			destFolder = new File( anomaliesPath );
		} else {
			destFolder = destFolderPath.toFile();
		}
		return new File( destFolder, "avaAnalysis.ser" );
	}

	private BctViolationsAnalysisConfiguration getConfiguration() {
		BctViolationsAnalysisConfiguration conf;

		conf = new BctViolationsAnalysisConfiguration();

		conf.setLogFilesNames(violationsAnalysisConfig.getLogFilesNames());
		conf.addFailingActions(analysisComposite.getFailingActionsIds());
		conf.addFailingTests(analysisComposite.getFailingTestsIds());
		conf.addFailingProcesses(analysisComposite.getFailingProcessesIds());
		conf.setAnomaliesGraphsPath(analysisComposite.getAnomalyGraphsFolder().getFullPath().toOSString());
		conf.defineFilteringStrategy(analysisComposite.getSelectedFilteringStrategy());
		conf.setAvaConfiguration(analysisComposite.getAvaConfiguration());

		if ( showActions ){
			ViolationsAnalysisResult vaResult = resultComposite.getViolationsAnalysisResult();
			conf.setViolationsAnalysisResult(vaResult);
		}

		conf.setFailureToAnalyze(analysisComposite.getAnalyzedFailureId());

		if ( regressionComposite != null ){
			conf.setFilteringResult(regressionComposite.getRegressionAnalysisResult());
		}

		return conf;
	}

	/* (non-Javadoc)
	 * Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}
	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {

		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);

		this.site = site;

		IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
		sourceFile = fileEditorInput.getFile();

		try {
			violationsAnalysisConfig = BctViolationsAnalysisDeserializer.deserialize(sourceFile.getLocation().toFile());

			monitoringConfiguration = MonitoringConfigurationRegistry.getInstance().getAssociatedMonitoringConfiguration(sourceFile);

		} catch (FileNotFoundException e) {
			MessageDialog.openError(site.getShell(), "Problem opening file", "Cannot open file "+sourceFile+" an exception occurred "+e.getMessage());

		}
		//			ArrayList<ViewerFilter> filter = new ArrayList<ViewerFilter>(1);
		//			filter.add( new ViewerFilter(){
		//
		//				@Override
		//				public boolean select(Viewer viewer, Object parentElement,
		//						Object element) {
		//
		//					System.out.println(element+" "+element.getClass().getCanonicalName());
		//					return true;
		//					//return element.toString().endsWith(".bctla");
		//				}
		//
		//			});
		//
		//
		//					
		//			filesToOpen.add(sourceFile.getLocation().toFile());
		//			IFile suggesteLAFile = sourceFile.getParent().getFile(new Path("BctViolationsLogAnalisys-"+System.currentTimeMillis()+".bctla"));
		//			
		//			IFile laFile = WorkspaceResourceDialog.openNewFile(getSite().getShell(), 
		//						"Select folder", "Select the folder where to save the configuration", 
		//						suggesteLAFile.getFullPath(), filter);
		//				
		//			if ( ! laFile.getName().endsWith(".bctla") ){
		//				 Path newPath = new Path(laFile.getFullPath().toOSString()+".bctla");
		//				 laFile = ResourcesPlugin.getWorkspace().getRoot().getFile(newPath);
		//			}	
		//			
		//			sourceFile = laFile;
		//			
		//			Path folderPath = new Path(sourceFile.getParent().getFullPath().toOSString()+"/"+sourceFile.getName().substring(0, sourceFile.getName().length()-6));
		//			IFile folderFile = ResourcesPlugin.getWorkspace().getRoot().getFile(folderPath);
		//			
		//			folderFile.getLocation().toFile().mkdirs();
		//			try {
		//				sourceFile.getParent().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		//			} catch (CoreException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//			anomalyGraphsFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(folderPath);
		//			System.out.println("AGF "+folderPath+" "+anomalyGraphsFolder );
		//		}
	}

	/* (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event){
		System.out.println(event.getType());
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
			Display.getDefault().asyncExec(new Runnable(){
				public void run(){
					IProgressMonitor progressMonitor = new NullProgressMonitor();
					doSave(progressMonitor);
				}            
			});
		}
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		setDirty(true);
	}


	@Override
	public boolean isDirty() {
		return dirty;
	}

}
