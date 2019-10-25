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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors;


import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.ConfigurationFilesManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.DefaultOptionsManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ActionsMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.TestCasesMonitoringOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.regressionAnalysis.CRegressionAnalysisUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.WorkspaceOptionsManager;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentsConfigurationComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.flattener.ObjectFlattenerPage;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.invariantGenerator.InvariantGeneratorOptionsComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring.ActionsMonitoringPage;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring.JavaResourcesMonitoringPage;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring.ResourcesPage;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.monitoring.TestCasesMonitoringPage;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.CConfigurationComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.CRegressionConfigurationComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.wizards.VARTConfigurationComposite;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.MultiPageEditorPart;


import util.componentsDeclaration.Component;
import util.componentsDeclaration.CppDemangledSignatureParser;
import util.componentsDeclaration.CppMangledSignatureParser;
import util.componentsDeclaration.SignatureParser;


public class BctMonitoringConfigurationEditor extends MultiPageEditorPart implements IResourceChangeListener, BCTObserver{



	public static final boolean STOP = true;

	private String regressionAnalysisPageName = "Regression Analysis Configuration";

	/** The font chosen in page 1. */
	private Font font;

	/** The text widget used in page 2. */
	private StyledText text;

	private ResourcesPage resourcePage;

	private ComponentsConfigurationComposite componentsConfiguration;

	private ObjectFlattenerPage flattenerConfiguration;

	private IEditorSite site;

	private IEditorInput editorInput;

	private IFile sourceFile;

	private MonitoringConfiguration loadedMonitoringConfiguration;

	private boolean dirty;

	private ActionsMonitoringPage actionsMonitoringOptionsPage;

	private TestCasesMonitoringPage testCasesOptionsPage;

	private JavaResourcesMonitoringPage javaResourcesMonitoringPage;

	private CRegressionConfigurationComposite cRegressionConfiguration;

	private CConfigurationComposite cConfiguration;

	private VARTConfigurationComposite vartConfiguration;

	private InvariantGeneratorOptionsComposite invariantGeneratorOptions;


	/**
	 * Creates a multi-page editor example.
	 */
	public BctMonitoringConfigurationEditor() {
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
	void createPage0() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);

		composite.setLayoutData(createFill());

		GridLayout pageLayout = new GridLayout();
		pageLayout.numColumns = 2;
		composite.setLayout(pageLayout);

		//layout.numColumns = 3;

		resourcePage = new ResourcesPage(composite);
		resourcePage.addBCTObserver(this);

		composite.pack();

		//editor = new TextEditor();
		int index = addPage(composite);
		setPageText(index, "Resource location");

		composite.setContent(resourcePage);	
		composite.setMinSize(resourcePage.computeSize(SWT.DEFAULT,SWT.DEFAULT));

	}
	/**
	 * Creates page 1 of the multi-page editor,
	 * which allows you to change the font used in page 2.
	 */
	void createPage1() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);

		
		
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		//		layout.numColumns = 3;

		
		
		componentsConfiguration = new ComponentsConfigurationComposite(composite,SWT.NONE);
		

		int index = addPage(composite);
		setPageText(index, "Components Configuration");

		composite.setContent(componentsConfiguration);	
		composite.setMinSize(new Point(400,400));
		//composite.setMinSize(componentsConfiguration.computeSize(SWT.DEFAULT,SWT.DEFAULT));

		
		componentsConfiguration.addBCTObserver(this);
	}


	void createPageCRegression() {
				ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
				composite.setExpandHorizontal(true);
				composite.setExpandVertical(true);

		cRegressionConfiguration = new CRegressionConfigurationComposite(composite,SWT.NONE);

		Button b = new Button(cRegressionConfiguration, SWT.NONE);
		b.setText("Refresh components to monitor");
		b.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				identifyComponentsToMonitor();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});



		cRegressionConfiguration.addBCTObserver(this);


		int index = addPage(composite);
		setPageText(index, regressionAnalysisPageName);

		composite.setContent(cRegressionConfiguration);	
		composite.setMinSize(new Point(400,400));
		//composite.setMinSize(cRegressionConfiguration.computeSize(SWT.DEFAULT,SWT.DEFAULT));

		
	}

	void createPageInferenceOptions() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);

		invariantGeneratorOptions = new InvariantGeneratorOptionsComposite(composite, SWT.DEFAULT, true );
		invariantGeneratorOptions.addBCTObserver(this);

		int index = addPage(composite);
		setPageText(index, "Inference Options");
		
		composite.setContent(invariantGeneratorOptions);	
		//composite.setMinSize(invariantGeneratorOptions.computeSize(SWT.DEFAULT,SWT.DEFAULT));
		composite.setMinSize(new Point(400,400));

	}
	
	

	void createPageVART() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);

		vartConfiguration = new VARTConfigurationComposite(composite,SWT.NONE);

		vartConfiguration.addBCTObserver(this);


		int index = addPage(composite);
		setPageText(index, "VART Configuration");
		
		composite.setContent(vartConfiguration);	
		composite.setMinSize(new Point(400,400));
		//composite.setMinSize(vartConfiguration.computeSize(SWT.DEFAULT,SWT.DEFAULT));

	}
	

	void createPageCMonitoring() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);


//		Composite composite = new Composite(getContainer(), SWT.NONE);

//		GridLayout layout = new GridLayout();
//		layout.numColumns = 1;
//		composite.setLayout(layout);
		
		cConfiguration = new CConfigurationComposite(composite,SWT.NONE);
		cConfiguration.addBCTObserver(this);

//		final ScrolledComposite sc2 = new ScrolledComposite(composite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
//		sc2.setExpandHorizontal(true);
//		sc2.setExpandVertical(true);
//		sc2.setAlwaysShowScrollBars(true);
//
//		cConfiguration = new CConfigurationComposite(sc2,SWT.NONE);
//		cConfiguration.addBCTObserver(this);
//		
//		sc2.setContent(cConfiguration);
//		sc2.setMinSize(cConfiguration.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
//		parent.setSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		composite.setContent(cConfiguration);	
		composite.setMinSize(cConfiguration.computeSize(SWT.DEFAULT,SWT.DEFAULT));

		int index = addPage(composite);
		setPageText(index, "C/C++ Monitoring Configuration");
	}


	private void identifyComponentsToMonitor() {

		Job job = new Job("BCT: identifying components to monitor"){
			@Override
			protected IStatus run(IProgressMonitor monitor) {

				try {

					//						ArrayList<Component> components = CRegressionAnalysisUtil.getComponentsToMonitor(loadedMonitoringConfiguration);
					ConfigurationFilesManager.updatRegressionComponentsToMonitor(loadedMonitoringConfiguration);

					Display.getDefault().asyncExec(new Runnable() {
						public void run(){
							try {
								componentsConfiguration.load(loadedMonitoringConfiguration.getComponentsConfiguration());
							} catch (ComponentManagerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} );
					//bctObservableUpdate(loadedMonitoringConfiguration.getComponentsConfiguration(), "Updated components to monitor"); 

				} catch (ConfigurationFilesManagerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//					} catch (IOException e) {
					//						// TODO Auto-generated catch block
					//						e.printStackTrace();

				}

				return Status.OK_STATUS;
			}
		};


		job.schedule();

	}

	/**
	 * Creates page 2 of the multi-page editor,
	 * which shows the sorted text.
	 */
	void createObjectFlattenerPage() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);

		//		GridLayout layout = new GridLayout();
		//		composite.setLayout(layout);
		//		layout.numColumns = 3;




		flattenerConfiguration = new ObjectFlattenerPage(composite,null);
		flattenerConfiguration.addBCTObserver(this);

		//composite.pack();
		int index = addPage(composite);
		setPageText(index, "Object Flattener Configuration");

		composite.setContent(flattenerConfiguration);	
		composite.setMinSize(flattenerConfiguration.computeSize(SWT.DEFAULT,SWT.DEFAULT));
		//composite.setMinSize(400,400);

	}


	void createPageX() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);



		//composite.setLayout(new FillLayout());


		Composite myComp = new Composite(composite,SWT.NONE);


		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 2;

		myComp.setLayout(layout);

		Label label = new Label(myComp,SWT.NONE);
		label.setText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

		label = new Label(myComp,SWT.NONE);
		label.setText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

		label = new Label(myComp,SWT.NONE);
		label.setText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");


		myComp.pack();

		composite.setContent(myComp);	
		composite.setMinSize(myComp.computeSize(SWT.DEFAULT,SWT.DEFAULT));
		//composite.setMinSize(400,400);


		int index = addPage(composite);
		setPageText(index, "XXXXXXXXXXXXXXX");
	}

	void createActionsMonitoringPage() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);


		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 3;


		actionsMonitoringOptionsPage = new ActionsMonitoringPage(composite,SWT.NONE);
		actionsMonitoringOptionsPage.addBCTObserver(this);

		int index = addPage(composite);
		setPageText(index, "Actions monitoring");




		// set content and minimum size
		composite.setContent(actionsMonitoringOptionsPage);	
		composite.setMinSize(actionsMonitoringOptionsPage.computeSize(SWT.DEFAULT,SWT.DEFAULT));


	}


	void createJavaResourceMonitoringPage() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);


		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 3;


		javaResourcesMonitoringPage = new JavaResourcesMonitoringPage(composite,SWT.NONE);
		javaResourcesMonitoringPage.addBCTObserver(this);

		int index = addPage(composite);
		setPageText(index, "Java resources monitoring");




		// set content and minimum size
		composite.setContent(javaResourcesMonitoringPage);	
		composite.setMinSize(javaResourcesMonitoringPage.computeSize(SWT.DEFAULT,SWT.DEFAULT));


	}

	void createTestCasesMonitoringPage() {
		ScrolledComposite composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);


		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 3;


		testCasesOptionsPage = new TestCasesMonitoringPage(composite,SWT.NONE);
		testCasesOptionsPage.addBCTObserver(this);

		int index = addPage(composite);
		setPageText(index, "Test cases monitoring");




		// set content and minimum size
		composite.setContent(testCasesOptionsPage);	
		composite.setMinSize(testCasesOptionsPage.computeSize(SWT.DEFAULT,SWT.DEFAULT));




	}

	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		
		BCTObservaleIncapsulated.disableObservers();
		
		createPage0();
		
		
		
		createPage1();

		

		//createPageX();


		if ( loadedMonitoringConfiguration != null ){

			createPageInferenceOptions();
			
			
			
			loadInvariantGeneratorOptions();
			
			if ( loadedMonitoringConfiguration.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.JavaMonitoring ){
				createObjectFlattenerPage();
				createJavaResourceMonitoringPage();
				createActionsMonitoringPage();
				createTestCasesMonitoringPage();

				javaResourcesMonitoringPage.load(loadedMonitoringConfiguration.getResourcesMonitoringOptions());
				testCasesOptionsPage.load(loadedMonitoringConfiguration.getTestCasesMonitoringOptions());
				actionsMonitoringOptionsPage.load(loadedMonitoringConfiguration.getActionsMonitoringOptions());
				flattenerConfiguration.load(loadedMonitoringConfiguration.getFlattenerOptions());
				
			}


			if ( loadedMonitoringConfiguration.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
				createPageCRegression();
				cRegressionConfiguration.load( (CRegressionConfiguration) loadedMonitoringConfiguration.getAdditionalConfiguration(CRegressionConfiguration.class), loadedMonitoringConfiguration );
			}
			
			if ( loadedMonitoringConfiguration.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
				createPageCMonitoring();
				cConfiguration.load( (CConfiguration) 
						loadedMonitoringConfiguration.getAdditionalConfiguration(CConfiguration.class), loadedMonitoringConfiguration );
			}
			
			if ( loadedMonitoringConfiguration.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.VART_Config ){
				regressionAnalysisPageName = "Monitoring Options";
				createPageCRegression();
				cRegressionConfiguration.load( (CRegressionConfiguration) loadedMonitoringConfiguration.getAdditionalConfiguration(CRegressionConfiguration.class), loadedMonitoringConfiguration );
				createPageVART();
				vartConfiguration.load( (VARTRegressionConfiguration) loadedMonitoringConfiguration.getAdditionalConfiguration(VARTRegressionConfiguration.class), loadedMonitoringConfiguration );
			}

			resourcePage.setResourceName(loadedMonitoringConfiguration.getConfigurationName());
			resourcePage.loadStorageConfiguration(loadedMonitoringConfiguration.getStorageConfiguration());
			resourcePage.setReferredProjectName(loadedMonitoringConfiguration.getReferredProjectName());

			
			try {
				componentsConfiguration.load(loadedMonitoringConfiguration.getComponentsConfiguration());
				String project = resourcePage.getReferredProjectName();
				if ( project != null && project.length() > 0 ){
					IProject iproject = ResourcesPlugin.getWorkspace().getRoot().getProject(project);

					//					IJavaProject javaProject = JavaCore.create(iproject);
					componentsConfiguration.setProject(iproject);
				}
			} catch (ComponentManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}




		}
		
		dirty = false;
		
		BCTObservaleIncapsulated.enableObservers();
	}

	public void loadInvariantGeneratorOptions() {
		Properties opts = loadedMonitoringConfiguration.getInvariantGeneratorOptions();
		if ( opts == null ){
			try {
				opts = WorkspaceOptionsManager.getWorkspaceOptions().getInvariantGeneratorOptions();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DefaultOptionsManagerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		invariantGeneratorOptions.load(opts);
		
		Properties fsaOptions = loadedMonitoringConfiguration.getFsaEngineOptions();
		invariantGeneratorOptions.loadFSAOptions(fsaOptions);
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

		String resourceName = resourcePage.getResourceName();
		StorageConfiguration sc = resourcePage.createStorageConfiguration();
		ComponentsConfiguration cc = componentsConfiguration.createComponentsConfiguration();

		FlattenerOptions fc = null;
		if ( flattenerConfiguration != null ){
			fc = flattenerConfiguration.getDefinedFlattenerOptions();
		}

		ActionsMonitoringOptions mo = null;
		if ( actionsMonitoringOptionsPage != null ){
			mo = actionsMonitoringOptionsPage.createMonitoringOptions();
		}

		TestCasesMonitoringOptions tcMo = null;
		if ( testCasesOptionsPage != null ){
			tcMo  = testCasesOptionsPage.createMonitoringOptions();
		}

		MonitoringConfiguration mrc = new MonitoringConfiguration(resourceName,sc,fc,cc,mo,tcMo);

		if (loadedMonitoringConfiguration != null){
			mrc.setConfigurationType(loadedMonitoringConfiguration.getConfigurationType());
		}

		String refPrj = resourcePage.getReferredProjectName();
		if ( refPrj != null && refPrj.length() > 0 ){
			mrc.setReferredProjectName(refPrj);
		}

		if (javaResourcesMonitoringPage != null ){
			mrc.setResourcesMonitoringOptions(javaResourcesMonitoringPage.createMonitoringOptions());
		}



		mrc.setInvariantGeneratorOptions( invariantGeneratorOptions.getUserDefinedOptions() );
		mrc.setFsaEngineOptions(invariantGeneratorOptions.getFSAEngineOptions());

		HashMap<Class, Object> additionalConfigs = new HashMap<Class, Object>();
		additionalConfigs.putAll(loadedMonitoringConfiguration.getAdditionalConfigurations());


		if ( this.cRegressionConfiguration != null ){
			setupCConfiguration( mrc, additionalConfigs, this.cRegressionConfiguration, CRegressionConfiguration.class );
		}

		if ( this.cConfiguration != null ){
			setupCConfiguration( mrc, additionalConfigs, this.cConfiguration, CConfiguration.class );
		}
		
		if ( this.vartConfiguration != null ){
			additionalConfigs.put(VARTRegressionConfiguration.class,vartConfiguration.getConfiguration());
		}


		mrc.setAdditionalConfigurations(additionalConfigs );

		try {

			MonitoringConfigurationSerializer.serialize(file.getLocation().toFile(), mrc);
			
			ConfigurationFilesManager.updateConfigurationFiles(mrc);
			ConfigurationFilesManager.updateMonitoringScripts(mrc);
			setClean();
		} catch (FileNotFoundException e) {
			//TODO:check if ok passing null
			MessageDialog.openError(null, "Error saving", "An error occurred while saving the configuration "+e.getMessage());

		} catch (ConfigurationFilesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MessageDialog.openError(null, "Error saving", "An error occurred while updating bct conf files "+e.getMessage());
		}

	}


	private <T  extends CConfiguration> void setupCConfiguration(
			MonitoringConfiguration mrc,
			HashMap<Class, Object> additionalConfigs,
			CConfigurationComposite cRegressionConfiguration, Class<T> clazz ) {
		CConfiguration regressionConfig = cRegressionConfiguration.getConfiguration();
		additionalConfigs.put(clazz,regressionConfig);

		SignatureParser signatureParser;
		if ( regressionConfig.isUseDemangledNames() ){
			//FIXME: Add demangled parser
			signatureParser= new CppDemangledSignatureParser();
		} else {
			signatureParser = new CppMangledSignatureParser();
		}

		for ( Component component : mrc.getComponentsConfiguration().getComponents() ){
			component.setSignatureParser(signatureParser);
		}
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
		this.editorInput = editorInput;
		IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
		sourceFile = fileEditorInput.getFile();

		loadedMonitoringConfiguration = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(sourceFile);
		if(loadedMonitoringConfiguration == null) {
			MessageDialog.openError(site.getShell(), "Problem opening file", "Cannot open file " + sourceFile + " file not found.");
			throw new PartInitException("Monitorig configuration file not found");
		}
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


	private synchronized void setClean(){
		dirty=false;
		this.firePropertyChange(PROP_DIRTY);
	}

	public synchronized void bctObservableUpdate(Object modifiedObject, Object message) {
		if ( !dirty ){
			dirty = true;
			this.firePropertyChange(PROP_DIRTY);
		}
	}


	@Override
	public boolean isDirty() {
		return dirty;
	}

}
