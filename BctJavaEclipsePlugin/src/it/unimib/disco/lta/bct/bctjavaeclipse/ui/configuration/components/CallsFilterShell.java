package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterComponent;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterGlobal;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;
import util.componentsDeclaration.MatchingRuleExclude;
import util.componentsDeclaration.MatchingRuleInclude;


public class CallsFilterShell implements BCTObservable {
	String comboValues[]={"","INCLUDE", "EXCLUDE"};
	private enum RuleTypes{INCLUDE,EXCLUDE};
	private BCTObservaleIncapsulated observable;

	Label nameComponentFilter;
	CCombo combo;;
	Table table;
	List<TableRow> rows = new ArrayList<TableRow>();
	Component component;
	private ComponentsConfigurationManager _componentManager;
	private Shell shell;
	private Label componentNameLabel;

	String[] componentNames;
	private HashMap<String, Component> componentNamesMap;
	HashMap<String,Integer> components = new HashMap<String,Integer>();

	private ArrayList<CallFilter> cf;

	public static class ComponentDefinitionShelException extends Exception {

		public ComponentDefinitionShelException() {
			super();
		}

		public ComponentDefinitionShelException(String message) {
			super(message);
		}

	}

	public class TableRow {
		Text textPackage;
		Text textClass;
		Text textMethod;
		private CCombo ruleTypeCombo;
		private CallsFilterShell parent;
		private CCombo fromComponentEditorCombo;


		TableRow( CallsFilterShell componentDefinitionShell ){
			TableEditor ruleTypeEditor;
			TableEditor methodEditor;
			TableEditor classEditor;
			TableEditor packageEditor;

			TableTextItemListener comboListener;	  
			TableItem item;
			TableTextItemListener list2;
			TableTextItemListener list3;
			this.parent = componentDefinitionShell;
			Table parentTable = componentDefinitionShell.table;
			item = new TableItem(parentTable, SWT.NONE);
			//item = table.getItem(i);


			packageEditor=new TableEditor(parentTable);
			textPackage=new Text(parentTable,SWT.NONE);
			TableTextItemListener list=new TableTextItemListener(textPackage,item,0);
			textPackage.addListener(SWT.Modify,list);
			textPackage.setToolTipText("Regular expression to match the package name.");
			packageEditor.grabHorizontal = true;  	
			packageEditor.setEditor(textPackage,item,0);

			classEditor=new TableEditor(parentTable);
			textClass=new Text(parentTable,SWT.NONE);
			list2=new TableTextItemListener(textClass,item,1);
			textClass.addListener(SWT.Modify,list2);
			textClass.setToolTipText("Regular expression to match the class name.");
			classEditor.grabHorizontal = true;
			classEditor.setEditor(textClass,item,1);

			methodEditor=new TableEditor(parentTable);
			textMethod=new Text(parentTable,SWT.NONE);

			methodEditor.grabHorizontal = true;
			methodEditor.setEditor(textMethod,item,2);
			list3=new TableTextItemListener(textMethod,item,2);
			textMethod.addListener(SWT.Modify,list3);
			textMethod.setToolTipText("Regular expression to match the method name.");

			ruleTypeEditor = new TableEditor(parentTable);
			ruleTypeCombo = new CCombo(parentTable, SWT.NONE);
			ruleTypeCombo.setItems(comboValues);
			ruleTypeCombo.setEditable(false);
			ruleTypeCombo.setToolTipText("Set INCLUDE if the regular expressions in this line match classes/methods that belong to the component." +
					"\n\nSet EXCLUDE if the regular expressions in this line match classes/methods that do not belong to the component." +
					"\n\nNotice that filters are run from top to bottom. " +
					"\nA class that matches an INCLUDE rule " +
					"is considered part of the component even if it matches following EXCLUDE rules." +
					"\nA class that matches an EXCLUDE rule is considered not part of a component even if it matches following INCLUDE rules." +
					"\nA class that does not match any rule is considered not being part of this component.");
			comboListener=new TableTextItemListener(ruleTypeCombo,item,3);
			ruleTypeCombo.addListener(SWT.Modify, comboListener);
			ruleTypeEditor.grabHorizontal = true;
			ruleTypeEditor.setEditor(ruleTypeCombo, item, 3);





			TableEditor fromComponentEditor = new TableEditor(parentTable);
			fromComponentEditorCombo = new CCombo(parentTable, SWT.NONE);
			fromComponentEditorCombo.setItems(componentNames);
			fromComponentEditorCombo.setEditable(false);
			fromComponentEditorCombo.setToolTipText("Indicates the invocation context to which this rule applies, i.e. the components from which the filtered method is invoked. " +
					"\nFor now we permit only a coarse granularity: you can filter out only invocations made from any component.");


			TableTextItemListener fromComponentEditorComboListener = new TableTextItemListener(ruleTypeCombo,item,3);
			fromComponentEditorCombo.addListener(SWT.Modify, fromComponentEditorComboListener);
			fromComponentEditor.grabHorizontal = true;
			fromComponentEditor.setEditor(fromComponentEditorCombo, item, 4);




			ruleTypeCombo.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					switch (e.detail) {
					case SWT.TRAVERSE_TAB_NEXT:
					case SWT.TRAVERSE_TAB_PREVIOUS: {
						parent.rowTraversed(getRow());
					}
					}
				}
			});


			fromComponentEditorCombo.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					switch (e.detail) {
					case SWT.TRAVERSE_TAB_NEXT:
					case SWT.TRAVERSE_TAB_PREVIOUS: {
						parent.rowTraversed(getRow());
					}
					}
				}
			});

		}

		TableRow getRow(){
			return this;
		}


		void setValues( String packageRule, String classRule, String methodRule, RuleTypes ruleType, String component ){
			System.out.println("Setting value "+packageRule+" "+classRule+" "+" "+methodRule+" "+ruleType);
			textPackage.setText(packageRule);
			textClass.setText(classRule);
			textMethod.setText(methodRule);
			int index;
			if ( ruleType.equals(RuleTypes.INCLUDE)){
				index = 1;
			} else {
				index = 2;
			}

			ruleTypeCombo.select(index);

			fromComponentEditorCombo.select(getComponentPosition(component));
		}

		private int getComponentPosition(String component) {
			return components.get(component);
		}


	}


	private static final int tableRows = 1;


	public void setComponentNameLabelText( String text){
		componentNameLabel.setText(text);
	}



	public List<CallFilter> getCallFilters() {
		
		
		HashMap<String,CallFilter> callFilters = new HashMap<String, CallFilter>();

		for ( TableRow row : rows ){

			String classExp = row.textClass.getText();
			String packExp = row.textPackage.getText();
			String methExp = row.textMethod.getText();

			int rsel = row.ruleTypeCombo.getSelectionIndex();
			int fsel = row.fromComponentEditorCombo.getSelectionIndex();
			
			if ( classExp.isEmpty() && packExp.isEmpty() && methExp.isEmpty() && row.ruleTypeCombo.getSelectionIndex() <= 0 && row.fromComponentEditorCombo.getSelectionIndex() == -1 ){
				break;
			}
			
			System.out.println(row.ruleTypeCombo.getSelectionIndex());
			
			if ( classExp.isEmpty() || packExp.isEmpty() || methExp.isEmpty() || row.ruleTypeCombo.getSelectionIndex() <= 0 || row.fromComponentEditorCombo.getSelectionIndex() == -1 ){
				System.out.println(row.ruleTypeCombo.getSelectionIndex());
				return null;
			}
			
			//build the rule
			MatchingRule rule;
			if ( row.ruleTypeCombo.getSelectionIndex() == 1 ){
				rule = new MatchingRuleInclude(packExp,classExp,methExp);
			} else {
				rule = new MatchingRuleExclude(packExp,classExp,methExp);
			}

			//Retrieve the callFilter
			String from = row.fromComponentEditorCombo.getText();
			if ( from != null ){
				CallFilter callFilter = callFilters.get(from);
				if ( callFilter == null ){
					if ( from.equals("*") ){
						callFilter = new CallFilterGlobal();
					} else {
						Component component = componentNamesMap.get(from);
						callFilter = new CallFilterComponent(component);
					}
					callFilters.put(from, callFilter);
				}
				callFilter.addRule(rule);
			}
		}


		cf = new ArrayList<CallFilter>(callFilters.size());
		cf.addAll(callFilters.values());
		return cf;
	}


	public void loadCallFilters(Collection<CallFilter> filters) {
		int availLines = table.getItemCount();
		int i = 0;
		for( CallFilter f : filters  ){
			String componentName;
			if ( f.isCallFilterComponent() ){
				CallFilterComponent cc = (CallFilterComponent) f;
				if ( cc == null ){
					continue;
				}
				componentName = cc.getCallFilterComponent().getName();
			} else {
				componentName = "*";
			}

			for ( MatchingRule callToR : f.getCallToRules() ){
				RuleTypes ruleType;
				if ( callToR instanceof MatchingRuleInclude ){
					ruleType = RuleTypes.INCLUDE;
				} else {
					ruleType = RuleTypes.EXCLUDE;
				}
				if ( i < availLines ){
					setTableRow( i, callToR.getPackageExpr(),callToR.getClassExpr(),callToR.getMethodExpr(),ruleType, componentName );
				} else {
					addRow(callToR.getPackageExpr(),callToR.getClassExpr(),callToR.getMethodExpr(),ruleType, componentName);
				}
			}
			i++;
		}
	}


	private void addRow(String packageRule, String classRule,
			String methodRule, RuleTypes ruleType, String component) {
		TableRow row = new TableRow(this);
		row.setValues(packageRule, classRule, methodRule, ruleType, component);
		rows.add(row);
	}

	private void addRow(){
		TableRow row = new TableRow(this);
		rows.add(row);	
	}

	protected void rowTraversed(TableRow row) {
		if ( rows.get(rows.size()-1)==row){
			addRow();
		}
	}


	public CallsFilterShell() {
		this(new ArrayList<>(),new ArrayList<>());
	}

	public CallsFilterShell(Collection<Component> components, Collection<CallFilter> callFilters) {
		observable= new BCTObservaleIncapsulated(this);

		

		cacheComponentNames(components);




		shell = new Shell(SWT.APPLICATION_MODAL|SWT.DIALOG_TRIM);
		shell.setSize(650, 550);
		observable= new BCTObservaleIncapsulated(this);

		//Composite main = new Composite(shell, SWT.NONE); 
		Shell main = shell;


		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		main.setLayout(layout);



		componentNameLabel=new Label(main,SWT.NONE);
		componentNameLabel.setText("Call filters");
		
		new Label(main,SWT.NONE);

		nameComponentFilter =new Label(main, SWT.NONE);
		GridData ld = new GridData(GridData.FILL_HORIZONTAL);
		ld.horizontalSpan=1;
		nameComponentFilter.setLayoutData(ld);
		


		Label label=new Label(main,SWT.NONE);
		ld = new GridData(GridData.FILL_HORIZONTAL);
		ld.horizontalSpan=2;
		label.setLayoutData(ld);
		label.setText("Classes:");


		table = new Table(main, SWT.BORDER | SWT.MULTI | SWT.VIRTUAL );
		ld = new GridData(GridData.FILL_BOTH);
		ld.horizontalSpan=2;
		table.setLayoutData(ld);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setToolTipText("Put in this table the regular expressions that match the classes/methods belonging to this component.");


		TableColumn packageName;
		packageName = new TableColumn(table, SWT.NONE, 0);
		packageName.setText("Package");
		packageName.setWidth(150);

		TableColumn className = new TableColumn(table, SWT.NONE, 1);
		className.setText("Class");
		className.setWidth(150);

		TableColumn methodName = new TableColumn(table, SWT.NONE, 2);
		methodName.setText("Method");
		methodName.setWidth(150);

		TableColumn rules = new TableColumn(table, SWT.NONE, 3);
		rules.setText("Rules");
		rules.setWidth(100);

		TableColumn from = new TableColumn(table, SWT.NONE, 4);
		from.setText("From");
		from.setWidth(100);

		for(int i=0;i<tableRows;i++)
		{
			addRow();	  
		}



		Button okButton=new Button(main, SWT.PUSH);
		okButton.setText("Ok");
		//okButton.setBounds(400,480,80,25);
		okButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent ev) {
				try {
					okButtonPressed();
				} catch (ComponentManagerException e) {
					MessageDialog.openError(shell, "Error", "Cannot add the component: "+e.getMessage());
				} catch (ComponentDefinitionShelException e) {
					MessageDialog.openError(shell, "Error", "Cannot add the component: "+e.getMessage());
				}
			}

		});

		Button cancelButton=new Button(main, SWT.PUSH);
		cancelButton.setText("Cancel");
		//cancelButton.setBounds(500,480,80,25);
		cancelButton.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {

			}

			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}

		});


		if ( callFilters != null ){
			loadCallFilters(callFilters);
		}
		//main.pack();
		//System.out.println("Created");

		table.update();
		System.out.println("UPDATE DONE");

	}



	private void cacheComponentNames(Collection<Component> components) {
		componentNamesMap = new HashMap<String,Component>();
		componentNames = new String[components.size()+1];

		componentNames[0]="*";
		int i=0;
		for( Component c : components ){
			i++;
			componentNames[i]=c.getName();
			componentNamesMap.put(componentNames[i], c);
		}

		for ( i = 0; i < componentNames.length; i++ ){
			this.components.put(componentNames[i], i);
		}
	}


	private void okButtonPressed() throws ComponentManagerException, ComponentDefinitionShelException{
		List<CallFilter> newCf = getCallFilters();
		if ( newCf == null ){
			MessageDialog.openError(shell, "", "Incomplete matching expressions");
			return;
		}
		observable.notifyBCTObservers(null);
		shell.close();
	}

	private void setTableRow( int line,String packageRule, String classRule, String methodRule, RuleTypes ruleType, String component) {
		rows.get(line).setValues(packageRule, classRule, methodRule, ruleType, component);
	}

	




	public void close() {
		shell.close();
	}

	public void open() {
		shell.open();
	}

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}



}
