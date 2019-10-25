package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import failureDetection.Failure;
import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngine;
import it.unimib.disco.lta.ava.engine.configuration.AvaConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.klfa.KlfaAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.AvaAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.BctViolationsAnalysisConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ModelsCleaner;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ViolationsAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.ava.AvaConfigurationUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.AnalysisResultListener;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.AnalysisResultListenersNotifierRunnable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.BctModelViolationsAnalysisRunnableWithProgress;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.BctRegressionAnalysisRunnableWithProgress;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.RegressionAnalysisResult;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.BctJavaEclipsePlugin;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.ava.configuration.AvaSettingsDialog;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.run.BctAvaAnalysisRunnableWithProgress;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.run.BctKLFAAnalysisRunnableWithProgress;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import modelsViolations.BctAnomalousCallSequence;
import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.draw2d.ChangeEvent;
import org.eclipse.draw2d.ChangeListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.SelectionDialog;

import tools.violationsAnalyzer.BctViolationsAnalyzer;
import tools.violationsAnalyzer.BctViolationsManager;
import tools.violationsAnalyzer.FailuresManager;
import tools.violationsAnalyzer.filteringStrategies.BctRuntimeDataFilter;
import tools.violationsAnalyzer.filteringStrategies.BctRuntimeDataFilterCorrectOut;
import tools.violationsAnalyzer.filteringStrategies.IdManager;
import tools.violationsAnalyzer.filteringStrategies.IdManagerAction;
import tools.violationsAnalyzer.filteringStrategies.IdManagerProcess;
import tools.violationsAnalyzer.filteringStrategies.IdManagerTest;

public class BctLogAnalysisComposite extends Composite implements BCTObservable, BCTObserver {

	private class KlfaAnalysisListener implements AnalysisResultListener<KlfaAnalysisResult>{

		public void analysisFinished(KlfaAnalysisResult result) {
			
			List<BctModelViolation> violations = new ArrayList<BctModelViolation>();
			for ( BctFSAModelViolation violation : result.getOriginalInteractionViolations() ){
				violations.add(violation);
			}
			manager.removeData(violations);
			
			for ( BctFSAModelViolation violation : result.getRefinedInteractionViolations() ){
				manager.addDatum(violation);
			}
			
			dataComposite.setViolations(manager.getData());
			
			logDataChanged = true;
			
			changed();
		}

		public void analysisStartup() {
			KBehaviorEngine.class.getCanonicalName();
		}
		
	}
	
	private BctViolationsManager manager = new BctViolationsManager();

	private Button processButton;
	private Button actionsButton;
	private Button testsButton;
	private Combo failureToAnalyzeCombo;

	private BCTObservaleIncapsulated observable;

	private IContainer anomalyGraphsFolder;
	private BctViolationsLogDataComposite dataComposite;
	
	private ArrayList<AnalysisResultListener<RegressionAnalysisResult>> regressionAnalysisListeners = new ArrayList<AnalysisResultListener<RegressionAnalysisResult>>(1);
	
	private ArrayList<AnalysisResultListener<ViolationsAnalysisResult>> violationAnalysisListeners = new ArrayList<AnalysisResultListener<ViolationsAnalysisResult>>(1);

	private List<AnalysisResultListener<AvaAnalysisResult>> avaAnalysisListeners = new ArrayList<AnalysisResultListener<AvaAnalysisResult>>(1);

	private boolean notifyChanges = false;

	private List<ChangeListener> failureToAnalyzeChangeListeners = new ArrayList<ChangeListener>();

	private AvaConfiguration avaConfiguration;

	private MonitoringConfiguration monitoringConfiguration;
	private int previousSelectedFailureComboIdx = -1;
	
	private boolean logDataChanged = false;
	
	public boolean getLogDataChanged() {
		return logDataChanged;
	}

	public void setLogDataChanged(boolean anomaliesChanged) {
		this.logDataChanged = anomaliesChanged;
	}
	
	public void getLogData(){
		manager.getData();
		
	}
	
	public void  addRegressionAnalysisListener(AnalysisResultListener<RegressionAnalysisResult> listener){
		regressionAnalysisListeners.add(listener);
	}

	public BctLogAnalysisComposite(Composite parent, int style, boolean showActions) {
		super(parent, style);
		observable = new BCTObservaleIncapsulated(this);
		
		BctLogAnalysisComposite c = this;
		GridLayout parentLayout = new GridLayout();
		parentLayout.numColumns=2;
		parentLayout.marginWidth=0;
		parentLayout.marginHeight=0;

		//RowLayout parentLayout = new RowLayout();

		c.setLayout(parentLayout);

		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_END);
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		
		dataComposite = new BctViolationsLogDataComposite(c,SWT.NONE, showActions);
		dataComposite.setLayoutData(gridData);
		dataComposite.pack();
		dataComposite.addBCTObserver(this);

		Composite correlateComposite = new Composite(c,SWT.NONE);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		correlateComposite.setLayoutData(gridData);
		
		GridLayout corrLayout = new GridLayout();
		corrLayout.numColumns = 4;
		correlateComposite.setLayout(corrLayout);
		
		Label lab = new Label(correlateComposite,SWT.NONE);
		lab.setText("Correlate by:" );
		
		
		processButton = new Button(correlateComposite,SWT.CHECK);
		//processButton.setBounds(20, 20, 70, 20);
		processButton.setText("Processes");
		
		
		actionsButton = new Button(correlateComposite,SWT.CHECK);
		//actionsButton.setBounds(110,20,70,20);
		actionsButton.setText("Actions");

		testsButton = new Button(correlateComposite,SWT.CHECK);
		testsButton.setText("Tests");
		//testsButton.setBounds(200, 20, 70, 20);

		processButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				e.doit = failureToAnalyzeChanged();
				if ( ! e.doit ){
					processButton.setSelection(false);
					return;
				}
				actionsButton.setSelection(false);
				testsButton.setSelection(false);
				updateCombo(dataComposite.getFailingProcesses());
			}



		});

		testsButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				e.doit = failureToAnalyzeChanged();
				if ( ! e.doit ){
					testsButton.setSelection(false);
					return;
				}
				actionsButton.setSelection(false);
				processButton.setSelection(false);
				updateCombo(dataComposite.getFailingTests());
			}

		});

		actionsButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				e.doit = failureToAnalyzeChanged();
				if ( ! e.doit ){
					actionsButton.setSelection(false);
					return;
				}
				testsButton.setSelection(false);
				processButton.setSelection(false);
				updateCombo(dataComposite.getFailingActions());
			}



		});
		

		
		
		lab.pack();
		processButton.pack();
		
//		lab = new Label(group,SWT.NONE);
//		lab.setText("Failure :");

//		gridData = new GridData(GridData.VERTICAL_ALIGN_END);
//		gridData.horizontalSpan = 1;
//		gridData.horizontalAlignment = GridData.FILL;
//		ang.setLayoutData(gridData);

		lab = new Label(correlateComposite,SWT.None);
		lab.setText("Analyze failure :");
		
		GridData gl = new GridData(GridData.FILL_HORIZONTAL);
		gl.horizontalSpan = 3;
		
		failureToAnalyzeCombo = new Combo(correlateComposite, SWT.DROP_DOWN | SWT.BORDER);
		failureToAnalyzeCombo.setLayoutData(gl);
		
		//
		//failureToAnalyzeCombo.setBounds(100, 20, 100, 20);

		failureToAnalyzeCombo.addSelectionListener(
				new SelectionListener(){

					public void widgetDefaultSelected(SelectionEvent e) {
						
					}

					public void widgetSelected(SelectionEvent e) {
						
						e.doit = failureToAnalyzeChanged();
						if ( ! e.doit ){
							failureToAnalyzeCombo.select(previousSelectedFailureComboIdx);
						}
						previousSelectedFailureComboIdx = failureToAnalyzeCombo.getSelectionIndex();
					}
					
				});
		
		
		
		
		
//		failureToAnalyzeCombo.addModifyListener(
//				new ModifyListener(){
//
//			public void modifyText(ModifyEvent e) {
//				 
//				failureToAnalyzeChanged();
//			}
//
//			
//			
//		});
		GridData gridLayoutSpan = new GridData();
		Button b;
		
		if ( showActions ){
		lab = new Label(correlateComposite,SWT.NONE);
		lab.setText("Anomaly graph correlation");
		
		gridLayoutSpan.horizontalSpan = 3;
		//lab.setLayoutData(gridLayoutSpan);
		b = new Button(correlateComposite, SWT.PUSH);
		b.setText("Run");
		b.setLayoutData(gridLayoutSpan);
		b.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				buildAnomalyGraphPressed();
			}

		});
		
		lab = new Label(correlateComposite,SWT.NONE);
		lab.setText("Refine interaction anomalies");
		//lab.setLayoutData(gridLayoutSpan);
		b = new Button(correlateComposite, SWT.PUSH);
		b.setText("Run");
		b.setLayoutData(gridLayoutSpan);
		b.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				refineInteractionAnomaliesWithKLFA();
			}

			

		});
		
		
		
		lab = new Label(correlateComposite,SWT.NONE);
		lab.setText("Cleanup models");
		gridLayoutSpan = new GridData();
		gridLayoutSpan.horizontalSpan = 3;
		//lab.setLayoutData(gridLayoutSpan);
		b = new Button(correlateComposite, SWT.PUSH);
		b.setText("Run");
		b.setLayoutData(gridLayoutSpan);
		b.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				cleanUpModels();
			}

		});
		
		
		
		
		
		lab = new Label(correlateComposite,SWT.NONE);
		lab.setText("Interaction anomalies intepretation");
		//lab.setLayoutData(gridLayoutSpan);
		
		b = new Button(correlateComposite, SWT.PUSH);
		b.setText("Run");
		b.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				analyzeInteractionsWithAva();
			}

		});
		
		b = new Button(correlateComposite, SWT.PUSH);
		b.setText("Options");
		b.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				defineAVAOptions();
			}

			

		});
		
		lab = new Label(correlateComposite,SWT.NONE);
		}
		
		
		lab = new Label(correlateComposite,SWT.NONE);
		lab.setText("Regression Analysis");
		gridLayoutSpan = new GridData();
		gridLayoutSpan.horizontalSpan = 3;
		//lab.setLayoutData(gridLayoutSpan);
		b = new Button(correlateComposite, SWT.PUSH);
		b.setText("Run");
		b.setLayoutData(gridLayoutSpan);
		b.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				runRegressionAnalysis();
			}

		});
		
		correlateComposite.pack();
//		b.setBounds(200, 20, 20, 20);
	}
	
	protected void runRegressionAnalysis() {
		
		analyzeRegressionsPressed();
		
	}

	private void defineAVAOptions() {
		AvaSettingsDialog dialog  = new AvaSettingsDialog(getShell());
		if ( avaConfiguration != null ){
			dialog.loadAvaConfiguration(avaConfiguration);
		} else {
			dialog.loadAvaConfiguration(AvaConfigurationUtil.createDefaultAvaConfiguration(monitoringConfiguration));
		}
		
		
		AvaConfiguration conf = dialog.open();
		if ( conf != null ){
			avaConfiguration = conf;
			changed();
		} 
	}
	
	private boolean failureToAnalyzeChanged() {
		
//		if ( previousSelectedFailureComboIdx != -1 ) {
//			boolean result = MessageDialog.openConfirm(getShell(), "Confirm analysis change", "If you change the id of the analyzed failure, or the correlation creiteria, you will loose the previous analysis results for this configuration.");
//			if ( result == false ){
//				return false;
//			}
//		}
		
			
		//FIXME: this should be run in another thread
		ChangeEvent change = new ChangeEvent(failureToAnalyzeCombo, "Failure Id Changed");
		
		for ( ChangeListener changeListener : failureToAnalyzeChangeListeners  ){
			changeListener.handleStateChanged(change);
		}
		changed();
		return true;
	}
	
	private void refineInteractionAnomaliesWithKLFA() {
		int selected = failureToAnalyzeCombo.getSelectionIndex();
		if ( selected > -1 ){
			String fid = failureToAnalyzeCombo.getItem(selected);
			launchKLFAAnalysis(fid);
		}
	}
	
	private void launchKLFAAnalysis(String fid) {
		if ( ! checkForAnomalyGraphsFolder() ){
			return;
		}
		
		BctViolationsAnalyzer violationsAnalyzer = createViolationsAnalyzer();
		
		final KlfaAnalysisResult violationAnalysisResult = new KlfaAnalysisResult(fid);
		List<AnalysisResultListener<KlfaAnalysisResult>> klfaAnalysisListeners = new ArrayList<AnalysisResultListener<KlfaAnalysisResult>>();
		klfaAnalysisListeners.add(new KlfaAnalysisListener());
		
		
		final AnalysisResultListenersNotifierRunnable<KlfaAnalysisResult> analysisResultRunnable = new AnalysisResultListenersNotifierRunnable<KlfaAnalysisResult>(violationAnalysisResult,klfaAnalysisListeners );
		
		final IRunnableWithProgress runnable = new BctKLFAAnalysisRunnableWithProgress(violationsAnalyzer, dataComposite.getMonitoringConfiguration(), dataComposite.getAnomalousCallSequences(), fid, violationAnalysisResult);

		Job job = new Job("Bct Refine Interaction Violations"){
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					runnable.run(monitor);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					return Status.CANCEL_STATUS;
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}
		};

		
//		//Runner that notifies the ViolationsAnalysisListeners
		job.addJobChangeListener(new IJobChangeListener(){

			public void aboutToRun(IJobChangeEvent event) {

			}

			public void awake(IJobChangeEvent event) {

			}

			public void done(IJobChangeEvent event) {
				//Populate the result table after the analysis finished
				BctJavaEclipsePlugin.getDefault().getWorkbench().getDisplay().asyncExec(analysisResultRunnable);
			}

			public void running(IJobChangeEvent event) {

			}

			public void scheduled(IJobChangeEvent event) {


			}

			public void sleeping(IJobChangeEvent event) {

			}

		});

		job.schedule();
		
	}

	private void analyzeInteractionsWithAva() {
		int selected = failureToAnalyzeCombo.getSelectionIndex();
		if ( selected > -1 ){
			String fid = failureToAnalyzeCombo.getItem(selected);
			launchAVAAnalysis(fid);
		}
	}

	private void launchAVAAnalysis(String fid) {
		if ( ! checkForAnomalyGraphsFolder() ){
			return;
		}
		
		BctViolationsAnalyzer violationsAnalyzer = createViolationsAnalyzer();
		
		final AvaAnalysisResult violationAnalysisResult = new AvaAnalysisResult(fid);
		final AnalysisResultListenersNotifierRunnable<AvaAnalysisResult> analysisResultRunnable = new AnalysisResultListenersNotifierRunnable<AvaAnalysisResult>(violationAnalysisResult,avaAnalysisListeners );
		
		if ( avaConfiguration == null ){
			AvaConfiguration conf = AvaConfigurationUtil.createDefaultAvaConfiguration(monitoringConfiguration);
			avaConfiguration=conf;
		}
		
		final IRunnableWithProgress runnable = new BctAvaAnalysisRunnableWithProgress(avaConfiguration,dataComposite.getMonitoringConfiguration(),violationsAnalyzer, dataComposite.getAnomalousCallSequences(), fid, violationAnalysisResult);

		Job job = new Job("Bct Model Violations Analysis"){
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					runnable.run(monitor);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					return Status.CANCEL_STATUS;
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}
		};




//		//Runner that notifies the ViolationsAnalysisListeners
		job.addJobChangeListener(new IJobChangeListener(){

			public void aboutToRun(IJobChangeEvent event) {

			}

			public void awake(IJobChangeEvent event) {

			}

			public void done(IJobChangeEvent event) {
				//Populate the result table after the analysis finished
				BctJavaEclipsePlugin.getDefault().getWorkbench().getDisplay().asyncExec(analysisResultRunnable);
			}

			public void running(IJobChangeEvent event) {

			}

			public void scheduled(IJobChangeEvent event) {


			}

			public void sleeping(IJobChangeEvent event) {

			}

		});

		job.schedule();

	}

	private void updateCombo(List<String> newItems) {
		changed();
		failureToAnalyzeCombo.removeAll();
		for ( String s : newItems ){
			failureToAnalyzeCombo.add(s);
		}
		previousSelectedFailureComboIdx = failureToAnalyzeCombo.getSelectionIndex();
	}




	private void changed() {
		if ( notifyChanges ){
			observable.notifyBCTObservers(null);
		}
	}

	private void analyzeRegressionsPressed() {
		
		
		int selected = failureToAnalyzeCombo.getSelectionIndex();
		if ( selected > -1 ){
			BctViolationsAnalyzer analyzer = createViolationsAnalyzer();
			String fid = failureToAnalyzeCombo.getItem(selected);
			launchRegressionAnalysis(analyzer,fid);
		}
	}
	
	private void buildAnomalyGraphPressed() {
		
		
		int selected = failureToAnalyzeCombo.getSelectionIndex();
		if ( selected > -1 ){
			BctViolationsAnalyzer analyzer = createViolationsAnalyzer();
			String fid = failureToAnalyzeCombo.getItem(selected);
			launchViolationsAnalysis(analyzer,fid);
		}
	}

	
	/**
	 * Checks if the result folder for anomaly graphs is set. 
	 * If not opens a popup that asks the user for the folder.
	 * 
	 * Returns true if the folder is set. False if not, in this case teh user pressed canceland do not want to go on with the analysis.
	 * 
	 * 
	 * @return
	 */
	private boolean checkForAnomalyGraphsFolder(){
		if ( anomalyGraphsFolder == null ){

			SelectionDialog dialog = new ContainerSelectionDialog(
					getShell(), ResourcesPlugin.getWorkspace().getRoot(), true, 
			"Select new file container");

			if (dialog.open() == ContainerSelectionDialog.OK) {
				Object[] result = dialog.getResult();

				if (result.length == 1) {
					anomalyGraphsFolder = ResourcesPlugin.getWorkspace().getRoot().getContainerForLocation((Path) result[0]);
				}
			}
		}
		
		
		
		if ( anomalyGraphsFolder == null ){ //cancel pressed
			return false;
		}
	
		return true;
	}
	
	
	private void launchRegressionAnalysis(BctViolationsAnalyzer analyzer, String fid) {
		
		if ( ! checkForAnomalyGraphsFolder() ){
			return;
		}
				
		
		RegressionAnalysisResult violationAnalysisResult = new RegressionAnalysisResult(fid);
		
		
		final AnalysisResultListenersNotifierRunnable<RegressionAnalysisResult> analysisResultRunnable = new AnalysisResultListenersNotifierRunnable<RegressionAnalysisResult>(violationAnalysisResult ,regressionAnalysisListeners );

		final IRunnableWithProgress runnable = new BctRegressionAnalysisRunnableWithProgress(anomalyGraphsFolder, monitoringConfiguration, analyzer, fid, violationAnalysisResult);

		Job job = new Job("Bct Regression Analysis"){
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					runnable.run(monitor);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					return Status.CANCEL_STATUS;
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}
		};




		//Runner that notifies the ViolationsAnalysisListeners
		job.addJobChangeListener(new IJobChangeListener(){

			public void aboutToRun(IJobChangeEvent event) {

			}

			public void awake(IJobChangeEvent event) {

			}

			public void done(IJobChangeEvent event) {
				//Populate the result table after the analysis finished
				BctJavaEclipsePlugin.getDefault().getWorkbench().getDisplay().asyncExec(analysisResultRunnable);
			}

			public void running(IJobChangeEvent event) {

			}

			public void scheduled(IJobChangeEvent event) {


			}

			public void sleeping(IJobChangeEvent event) {

			}

		});

		job.schedule();


	}

	/**
	 * This method
	 * 	launches a bct model violation analysis through the BctModelViolationsAnalysisRunnableWithProgress
	 * 	add a job change listener that after the analysis is finished populates the result table
	 *  
	 * @param analyzer
	 * @param fid
	 */
	private void launchViolationsAnalysis(BctViolationsAnalyzer analyzer, String fid) {
		
		if ( ! checkForAnomalyGraphsFolder() ){
			return;
		}
				
		final ViolationsAnalysisResult violationAnalysisResult = new ViolationsAnalysisResult(fid);
		final AnalysisResultListenersNotifierRunnable<ViolationsAnalysisResult> analysisResultRunnable = new AnalysisResultListenersNotifierRunnable<ViolationsAnalysisResult>(violationAnalysisResult,violationAnalysisListeners);

		final IRunnableWithProgress runnable = new BctModelViolationsAnalysisRunnableWithProgress(anomalyGraphsFolder, analyzer, fid, violationAnalysisResult);

		Job job = new Job("Bct Model Violations Analysis"){
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					runnable.run(monitor);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					return Status.CANCEL_STATUS;
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}
		};




		//Runner that notifies the ViolationsAnalysisListeners
		job.addJobChangeListener(new IJobChangeListener(){

			public void aboutToRun(IJobChangeEvent event) {

			}

			public void awake(IJobChangeEvent event) {

			}

			public void done(IJobChangeEvent event) {
				//Populate the result table after the analysis finished
				BctJavaEclipsePlugin.getDefault().getWorkbench().getDisplay().asyncExec(analysisResultRunnable);
			}

			public void running(IJobChangeEvent event) {

			}

			public void scheduled(IJobChangeEvent event) {


			}

			public void sleeping(IJobChangeEvent event) {

			}

		});

		job.schedule();


	}

	
	/**
	 * Creates a violations analyzer properly configured
	 * 
	 * @return
	 */
	private BctViolationsAnalyzer createViolationsAnalyzer() {
		FailuresManager fm = new FailuresManager();

		for ( String id : dataComposite.getFailingProcesses() ){
			fm.addFailingProcess(id);
		}

		for ( String id : dataComposite.getCorrectProcesses() ){
			fm.addCorrectProcess(id);
		}

		for ( String id : dataComposite.getFailingActions() ){
			fm.addFailingAction(id);
		}

		for ( String id : dataComposite.getCorrectActions() ){
			fm.addCorrectAction(id);
		}

		for ( String id : dataComposite.getFailingTests() ){
			fm.addFailingTest(id);
		}

		for ( String id : dataComposite.getCorrectTests() ){
			fm.addCorrectTest(id);
		}


		//For now create the correct out filtering strategy, in the future we will insert other filtering strategies
		//Probably we will put the default choiche in the BctCOnfiguration and let the user change through a menu openable from the composite
		BctRuntimeDataFilter filter = new BctRuntimeDataFilterCorrectOut();

		return new BctViolationsAnalyzer(manager,fm, filter,getSelectedFilteringStrategy());

	}

	public IdManager getSelectedFilteringStrategy() {
		if ( processButton.getSelection() ){
			return IdManagerProcess.INSTANCE;
		} else if (testsButton.getSelection()) {
			return IdManagerTest.INSTANCE;
		} else {
			return IdManagerAction.INSTANCE;
		}

	}
		
	public  void selectFilteringStrategy( IdManager filteringStrategy ) {
		processButton.setSelection(false);
		actionsButton.setSelection(false);
		testsButton.setSelection(false);
		boolean selected = false;
		
		if ( filteringStrategy == IdManagerProcess.INSTANCE ){
			if ( dataComposite.getProcessesTableItems().size() > 0 ){
				selected = true;
				processButton.setSelection(true);
				updateCombo(dataComposite.getFailingProcesses());
			}
		} else if ( filteringStrategy == IdManagerTest.INSTANCE ) {
			if ( dataComposite.getTestsTableItems().size() > 0 ){
				selected = true;
				testsButton.setSelection(true);
				updateCombo(dataComposite.getFailingTests());
			}
		} else if ( filteringStrategy == IdManagerAction.INSTANCE ) {
			if ( dataComposite.getActionsTableItems().size() > 0 ){
				selected = true;
				actionsButton.setSelection(true);
				updateCombo(dataComposite.getFailingActions());
			}
		}

		if ( ! selected ){
			if ( dataComposite.getProcessesTableItems().size() > 0 ){
				processButton.setSelection(true);
				updateCombo(dataComposite.getFailingProcesses());
			} else if ( dataComposite.getTestsTableItems().size() > 0 ){
				testsButton.setSelection(true);
				updateCombo(dataComposite.getFailingTests());
			} else if ( dataComposite.getActionsTableItems().size() > 0 ){
				actionsButton.setSelection(true);
				updateCombo(dataComposite.getFailingActions());
			}
		}
	}	

	public void loadFiles(List<File> list) {
		dataComposite.loadFiles(list);
					
		for (File file : list) {
			IPath path = new Path(file.getAbsolutePath());
			IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocation(path);
			monitoringConfiguration = MonitoringConfigurationRegistry.getInstance().getAssociatedMonitoringConfiguration(files[0]);
			dataComposite.setMonitoringConfiguration(monitoringConfiguration);
		}
		
		for ( BctModelViolation modelViolation : dataComposite.getModelsViolations() ){
			manager.addDatum(modelViolation);
		}
		
		
	}
	
	
	
	private void cleanUpModels(){
		ModelsCleaner mc = new ModelsCleaner();
		mc.cleanupModels( monitoringConfiguration, dataComposite.getViolations() );
		
	}
	

	public void setAnomalyGraphsFolder(IContainer anomalyGraphsFolder) {
		this.anomalyGraphsFolder = anomalyGraphsFolder;
	}

	public IContainer getAnomalyGraphsFolder() {
		return anomalyGraphsFolder;
	}



	public void widgetSelected(SelectionEvent e) {
		observable.notifyBCTObservers(null);
	}

	public void unCheckAll() {
		dataComposite.unCheckAll();
	}

	

	public void selectFailureId(String failureId) {
		if ( failureId == null ){
			return;
		}
		
		int idx = failureToAnalyzeCombo.indexOf(failureId);
		if ( idx  >= 0 ){
			failureToAnalyzeCombo.select(idx);
		}
		previousSelectedFailureComboIdx = idx;
	}

	public BctViolationsLogDataComposite getDataComposite() {
		return dataComposite;
	}

	public void setDataComposite(BctViolationsLogDataComposite dataComposite) {
		this.dataComposite = dataComposite;
	}

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}

	public void selectActions(Set<String> failingActions) {
		dataComposite.selectActions(failingActions);
	}

	public void selectProcesses(Set<String> failingProcesses) {
		dataComposite.selectProcesses(failingProcesses);
	}

	public void selectTests(Set<String> failingTests) {
		dataComposite.selectTests(failingTests);
	}

	public Set<String> getFailingActionsIds() {
		return dataComposite.getFailingActionsIds();
	}

	public Set<String> getFailingProcessesIds() {
		return dataComposite.getFailingProcessesIds();
	}

	public Set<String> getFailingTestsIds() {
		return dataComposite.getFailingTestsIds();
	}

	/**
	 * Add a violations analysis listener. The listener is called when a new analysis is completed.
	 * 
	 * @param listener
	 */
	public void addViolationsAnalysisListener(AnalysisResultListener<ViolationsAnalysisResult> listener) {
		violationAnalysisListeners.add(listener);
	}
	
	/**
	 * Add an AVA analysis listener.
	 * @param listener
	 */
	public void addAvaAnalysisListener(AnalysisResultListener<AvaAnalysisResult> listener) {
		avaAnalysisListeners.add(listener);
	}

	/**
	 * Remove the listener from the list of analysis listeners
	 * @param listener
	 * @return
	 */
	public boolean removeViolationsAnalysisListener(AnalysisResultListener<ViolationsAnalysisResult> listener) {
		return violationAnalysisListeners.remove(listener);
	}
	
	/**
	 * Remove the listener from the list of AVA analysis listeners
	 * @param listener
	 * @return
	 */
	public boolean removeAVAAnalysisListener(AnalysisResultListener<AvaAnalysisResult> listener) {
		return avaAnalysisListeners.remove(listener);
	}

	public void setSite(IEditorSite site) {
		dataComposite.setSite(site);
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		if ( modifiedObject == dataComposite ){
			updateFailureIdCombo();
		}
		changed();
	}

	private void updateFailureIdCombo() {
		if ( processButton.getSelection() ){
			updateCombo(dataComposite.getProcessesTableItems());
		} else if ( actionsButton.getSelection() ){
			updateCombo(dataComposite.getActionsTableItems());
		} else if ( testsButton.getSelection() ){
			updateCombo(dataComposite.getTestsTableItems());
		}
	}

	public boolean addFailureToAnalyzeChangeListener(ChangeListener changeListener){
		return failureToAnalyzeChangeListeners.add(changeListener);
	}
	
	public boolean removeFailureToAnalyzeChangeListener(ChangeListener changeListener){
		return failureToAnalyzeChangeListeners.remove(changeListener);
	}

	public void load(
			BctViolationsAnalysisConfiguration violationsAnalysisConfig2) {
		
		
		
		loadFiles(violationsAnalysisConfig2.retrieveLogFiles());
		
		unCheckAll();
		selectProcesses(violationsAnalysisConfig2.getFailingProcesses());
		selectActions(violationsAnalysisConfig2.getFailingActions());
		selectTests(violationsAnalysisConfig2.getFailingTests());
		
		String agp = violationsAnalysisConfig2.getAnomaliesGraphsPath();
		setAnomalyGraphsFolder(ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(agp)));
		
		selectFilteringStrategy(violationsAnalysisConfig2.retrieveFilteringStrategy());
		
		
		
		selectFailureId(violationsAnalysisConfig2.getFailureToAnalyze());
		
		avaConfiguration = violationsAnalysisConfig2.getAvaConfiguration();
		notifyChanges = true;
	}

	public AvaConfiguration getAvaConfiguration() {
		return avaConfiguration;
	}

	public String getAnalyzedFailureId() {
		return failureToAnalyzeCombo.getText();
	}

	public List<Failure> getFailures(){
		return dataComposite.getFailures();
	}

	public List<BctAnomalousCallSequence> getAnomalousCallSequences() {
		return dataComposite.getAnomalousCallSequences();
	}

	public List<BctModelViolation> getViolations() {
		return dataComposite.getViolations();
	}

	public MonitoringConfiguration getMonitoringConfiguration() {
		return monitoringConfiguration;
	}

}
