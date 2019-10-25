package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import failureDetection.Failure;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.FilterCommand.FilteringAttribute;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import modelsViolations.BctAnomalousCallSequence;
import modelsViolations.BctModelViolation;
import modelsViolations.BctRuntimeData;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorSite;

import tools.violationsAnalyzer.BctViolationsLogData;
import tools.violationsAnalyzer.CBEBctViolationsLogLoader;
import tools.violationsAnalyzer.CBEBctViolationsLogLoaderException;

/**
 * This composite shows information about recorded failures and provides the controls to start the analysis
 * 
 * @author Fabrizio Pastore
 *
 */
public class BctViolationsLogDataComposite extends Composite implements BCTObservable, SelectionListener, BCTObserver {

	private FailuresTableChecked processesTable;
	private FailuresTableChecked actionsTable;
	private FailuresTableChecked testsTable;
	private ViolationsComposite violationsComposite;
	private List<File> files = new ArrayList<File>();
	//private ArrayList<BctModelViolation> modelsViolations = new ArrayList<BctModelViolation>();
	private BCTObservaleIncapsulated observable;
	private BctViolationsLogData logData;
	private List<BctAnomalousCallSequence> anomalousCallSequences =  new ArrayList<BctAnomalousCallSequence>();
	private MonitoringConfiguration monitoringConfiguration;
	private Label processesLabel;
	private Label actionsLabel;
	private Label testsLabel;
	private Label violationsLabel;
	private boolean showActions;

	public MonitoringConfiguration getMonitoringConfiguration() {
		return monitoringConfiguration;
	}

	public List<BctAnomalousCallSequence> getAnomalousCallSequences() {
		return anomalousCallSequences;
	}

	public BctViolationsLogDataComposite(Composite parent, int style, boolean showActions) {
		super(parent, style);
		this.showActions = showActions;
		// TODO Auto-generated constructor stub
		observable= new BCTObservaleIncapsulated(this);

		BctViolationsLogDataComposite group = this;

		processesLabel = new Label(group,SWT.NONE);
		updateProcessesLabel(0);
		processesLabel.setBounds(20, 20, 100, 20);

		int testStart = 180;
		int testWidth = 450;
		if ( showActions ){
			actionsLabel = new Label(group,SWT.NONE);
			updateActionsLabel(0);
			actionsLabel.setBounds(180, 20, 100, 20);
			testStart = 340;
			testWidth = 150;
		}

		testsLabel = new Label(group,SWT.NONE);
		updateTestsLabel(0);
		testsLabel.setBounds(testStart, 20, 100, 20);

		violationsLabel = new Label(group,SWT.NONE);
		updateViolationsLabel(0);
		violationsLabel .setBounds(650, 20, testWidth, 20);

		processesTable = new FailuresTableChecked(group,SWT.NONE,new String[]{});
		processesTable.setBounds(20, 50, 150, 300);
		processesTable.setFilteringAttribute(FilteringAttribute.Process);

		if ( showActions ){
			actionsTable = new FailuresTableChecked(group,SWT.NONE,new String[]{});
			actionsTable.setBounds(180, 50, 150, 300);
			actionsTable.setFilteringAttribute(FilteringAttribute.Action);
		}

		testsTable = new FailuresTableChecked(group,SWT.NONE,new String[]{});
		testsTable.setBounds(testStart, 50, testWidth, 300);
		testsTable.setFilteringAttribute(FilteringAttribute.Test);

		violationsComposite = new ViolationsComposite(group, SWT.BORDER);
		violationsComposite.setBounds(650, 50, 500, 300);

		group.pack();

		processesTable.addBCTObserver(this);
		if ( showActions ){
			actionsTable.addBCTObserver(this);
		}
		testsTable.addBCTObserver(this);
	}

	public void setSite(IEditorSite site) {
		violationsComposite.setSelectionProvider(site);
	}

	public List<String> getProcessesTableItems() {
		return processesTable.getTableItems();
	}

	public List<String> getActionsTableItems() {
		if ( showActions ){
			return actionsTable.getTableItems();
		}
		return new ArrayList<String>();
	}

	public List<String> getTestsTableItems() {
		return testsTable.getTableItems();
	}


	public List<String> getFailingProcesses() {
		return processesTable.getSelectedIds();
	}

	public List<String> getCorrectProcesses() {
		return processesTable.getUnSelectedIds();
	}

	public List<String> getFailingActions() {
		if ( showActions ){
			return actionsTable.getSelectedIds();
		}
		return new ArrayList<String>();
	}

	public List<String> getCorrectActions() {
		if ( showActions ){
			return actionsTable.getUnSelectedIds();
		}
		return new ArrayList<String>();
	}

	public List<String> getFailingTests() {
		return testsTable.getSelectedIds();
	}

	public List<String> getCorrectTests() {
		return testsTable.getUnSelectedIds();
	}


	public void removeAll() {
		processesTable.removeAll();
		if ( showActions ){
			actionsTable.removeAll();
		}
		testsTable.removeAll();
	}

	public void addProcesses(List<String> ids){
		processesTable.addItems(ids);
	}

	public void addActions(List<String> actions){
		actionsTable.addItems(actions);
	}

	public void addTests(List<String> tests){
		testsTable.addItems(tests);
	}



	public void unCheckAll() {
		processesTable.unCheckAll();
		testsTable.unCheckAll();
		if ( showActions ){
			actionsTable.unCheckAll();
		}
	}

	public void setViolations( Collection<BctModelViolation> collection ){
		logData.setViolations(collection);
		violationsComposite.setItems(collection);
		updateViolationsLabel(collection.size());
	}

	private void updateViolationsLabel(int size) {
		violationsLabel.setText("Violations ("+size+")");
	}

	private void updateTestsLabel(int size) {
		testsLabel.setText("Tests ("+size+")");
	}

	private void updateActionsLabel(int size) {
		if ( showActions ){
			actionsLabel.setText("Actions ("+size+")");
		}
	}

	private void updateProcessesLabel(int size) {
		processesLabel.setText("Processes ("+size+")");
	}

	/**
	 * This method load the cbe entities contained in the list of CBE files passed
	 * 
	 * TODO: this method should be moved elsewhere, this class should receive just a BctViolationLogData object and load its contents
	 * it should not create it
	 * 
	 * @param list
	 */
	public void loadFiles(List<File> list) {
		this.files.addAll(list);

		CBEBctViolationsLogLoader loader = new CBEBctViolationsLogLoader();

		try {
			logData = loader.load(list);

			processesTable.removeAll();
			Set<String> pids = logData.getProcessesIds();

			ArrayList<String> pidsList = new ArrayList<String>();
			pidsList.addAll(pids);
			Collections.sort(pidsList);

			processesTable.addItems(pidsList);
			updateProcessesLabel(pids.size());
			if ( showActions ){
				actionsTable.removeAll();
				Set<String> aids = logData.getActionsIds();
				actionsTable.addItems(aids);
				updateActionsLabel(aids.size());
			}

			testsTable.removeAll();
			Set<String> tids = logData.getTestsIds();
			ArrayList<String> sortedTests = new ArrayList<String>();
			sortedTests.addAll(tids);
			Collections.sort(sortedTests);
			
			testsTable.addItems(sortedTests);
			updateTestsLabel(tids.size());

			List<BctModelViolation> vids = logData.getViolations();
			violationsComposite.setItems(vids);
			updateViolationsLabel(vids.size());

			for ( Failure f : logData.getFailures() ){
				processesTable.setChecked(f.getFailingPID());
				if ( showActions ){
					actionsTable.setChecked(f.getFailingActionId());
				}
				testsTable.setChecked(f.getFailingTestId());
			}

			anomalousCallSequences.clear();
			List<BctAnomalousCallSequence> callSequences = logData.getAnomalousCallSequences();
			if ( callSequences != null ){
				anomalousCallSequences.addAll(callSequences);
			}

		} catch (CBEBctViolationsLogLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<String> getFailingActionsIds() {
		HashSet<String> s = new HashSet<String>();
		if ( showActions ){
			s.addAll(actionsTable.getSelectedIds());
		}
		return s;
	}

	public Set<String> getFailingProcessesIds() {
		HashSet<String> s = new HashSet<String>();
		s.addAll(processesTable.getSelectedIds());
		return s;
	}

	public Set<String> getFailingTestsIds() {
		HashSet<String> s = new HashSet<String>();
		s.addAll(testsTable.getSelectedIds());
		return s;
	}

	public List<BctModelViolation> getModelsViolations() {
		if ( logData == null ){
			return new ArrayList<BctModelViolation>(0);
		}
		return logData.getViolations();
	}

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}

	public void widgetDefaultSelected(SelectionEvent e) {

	}


	public void widgetSelected(SelectionEvent e) {
		observable.notifyBCTObservers(null);
	}


	public void selectProcesses(Set<String> failingProcesses) {
		processesTable.checkAll(failingProcesses);
	}

	public void selectActions(Set<String> failingActions) {
		if ( showActions ){
			actionsTable.checkAll(failingActions);
		}
	}

	public void selectTests(Set<String> failingTests) {
		testsTable.checkAll(failingTests);
	}


	public void bctObservableUpdate(Object modifiedObject, Object message) {
		if ( message instanceof  FilterCommand ){

			violationsComposite.applyFilter((FilterCommand)message);
			updateViolationsLabel( violationsComposite.getNumberOfShownElements() );

		} else if ( message instanceof  RemoveFilterCommand ){
			violationsComposite.removeFilter();
		} else {
			observable.notifyBCTObservers(message);
		}
	}

	public void setMonitoringConfiguration(MonitoringConfiguration associatedMonitoringConfiguration) {
		monitoringConfiguration = associatedMonitoringConfiguration; 
		violationsComposite.setMonitoringConfiguration(associatedMonitoringConfiguration);
	}

	public List<BctRuntimeData> getRuntimeData(){
		return logData.getAllRuntimeData();
	}

	public List<Failure> getFailures(){
		return logData.getFailures();
	}

	public List<BctModelViolation> getViolations() {
		return logData.getViolations();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}


}
