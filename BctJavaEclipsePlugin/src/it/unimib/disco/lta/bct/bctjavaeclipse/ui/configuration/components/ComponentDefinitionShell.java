package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;

import java.util.ArrayList;
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


public class ComponentDefinitionShell implements BCTObservable {
	String comboValues[]={"","INCLUDE", "EXCLUDE"};
	private enum RuleTypes{INCLUDE,EXCLUDE};
	private BCTObservaleIncapsulated observable;
	
	Text nameComponentFilter;
	CCombo combo;;
	Table table;
	List<TableRow> rows = new ArrayList<TableRow>();
	Component component;
	private ComponentsConfigurationManager componentManager;
	private Shell shell;
	private Label componentNameLabel;
	
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
		private ComponentDefinitionShell parent;
		
		
		TableRow( ComponentDefinitionShell componentDefinitionShell ){
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

		}
		
		TableRow getRow(){
			return this;
		}
		
		
		void setValues( String packageRule, String classRule, String methodRule, RuleTypes ruleType ){
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
		}
	}
	
	
	private static final int tableRows = 1;


	public void setComponentNameLabelText( String text){
		componentNameLabel.setText(text);
	}
	
	public ComponentDefinitionShell(ComponentsConfigurationManager componentManager, Component component) {
		this(componentManager);
		//create the table
		observable= new BCTObservaleIncapsulated(this);
		
		this.component = component;
		
//		: populate the table with info taken from component 
		nameComponentFilter.setText( component.getName());
		
		
		int line = 0;
		int availLines = table.getItemCount();
		for ( MatchingRule rule : component.getRules() ){
			
			
			String packageRule = rule.getPackageExpr();
			String classRule = rule.getClassExpr();
			String methodRule = rule.getMethodExpr();
			RuleTypes ruleType;
			
			
			if ( rule instanceof MatchingRuleInclude ){
				ruleType = RuleTypes.INCLUDE;
			} else {
				ruleType = RuleTypes.EXCLUDE;
			}
			
			if ( line < availLines ){
				System.out.println("LINE: "+line);
				setTableRow( line, packageRule, classRule, methodRule, ruleType );
			} else {
				addRow(packageRule, classRule, methodRule, ruleType);
			}
			
			
			++line;
		}
		
		table.update();
		System.out.println("UPDATE DONE");

	}
	
	private void addRow(String packageRule, String classRule,
			String methodRule, RuleTypes ruleType) {
		TableRow row = new TableRow(this);
		row.setValues(packageRule, classRule, methodRule, ruleType);
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

	
	public ComponentDefinitionShell(final ComponentsConfigurationManager componentManager) {
		shell = new Shell(SWT.APPLICATION_MODAL|SWT.DIALOG_TRIM);
		shell.setSize(650, 550);
		observable= new BCTObservaleIncapsulated(this);
		
		//Composite main = new Composite(shell, SWT.NONE); 
		Shell main = shell;
		this.componentManager = componentManager;
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		main.setLayout(layout);
		
//	    Group createComponent=new Group(main, SWT.NULL);
	    
		//createComponent.setLayoutData(ld);
//		createComponent.setText("Component name: ");
		//createComponent.setBounds(5,5,620,50);
		
		componentNameLabel=new Label(main,SWT.NONE);
		componentNameLabel.setText("Component name:");
		
		//nameComponent.setBounds(5,20,40,20);
		nameComponentFilter =new Text(main, SWT.SINGLE | SWT.BORDER);
		GridData ld = new GridData(GridData.FILL_HORIZONTAL);
		ld.horizontalSpan=1;
		nameComponentFilter.setLayoutData(ld);
		nameComponentFilter.setToolTipText("Abstract name of the component. " +
				"It can be any name.");
		//nameComponentFilter.setBounds(60,20,550,20);
		
//		int columns = 40;
//		GC gc = new GC (nameComponentFilter);
//		FontMetrics fm = gc.getFontMetrics ();
//		int width = columns * fm.getAverageCharWidth ();
//		int height = fm.getHeight ();
//		gc.dispose ();
//		nameComponentFilter.setSize(nameComponentFilter.computeSize (width, height));

		
		Label label=new Label(main,SWT.NONE);
		ld = new GridData(GridData.FILL_HORIZONTAL);
		ld.horizontalSpan=2;
		label.setLayoutData(ld);
		label.setText("Classes:");
		//Group filter=new Group(main, SWT.NONE);
		
//		ld = new GridData();
//		ld.horizontalSpan=2;
//		filter.setLayoutData(ld);
//		filter.setText("Elements: ");
//		filter.setBounds(5,60,620,400);
		
		table = new Table(main, SWT.BORDER | SWT.MULTI | SWT.VIRTUAL );
		ld = new GridData(GridData.FILL_BOTH);
		ld.horizontalSpan=2;
		table.setLayoutData(ld);
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setToolTipText("Put in this table the regular expressions that match the classes/methods belonging to this component.");
		//table.setBounds(10, 20, 580, 350);
		
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
		
		
		//main.pack();
		//System.out.println("Created");

	}

	
	private void okButtonPressed() throws ComponentManagerException, ComponentDefinitionShelException{
		System.out.println("Ok pressed");
		if ( component == null ){
			componentManager.addComponent( getDefinedComponent() );
		} else {
			component.setName(nameComponentFilter.getText());
			component.setRules(getRules());
		}
		observable.notifyBCTObservers(null);
		shell.close();
	}
	
	private void setTableRow( int line,String packageRule, String classRule, String methodRule, RuleTypes ruleType) {
		  rows.get(line).setValues(packageRule, classRule, methodRule, ruleType);
	}
	
	private List<MatchingRule> getRules() throws ComponentDefinitionShelException{
		java.util.List<MatchingRule> matchingRules = new ArrayList<MatchingRule>();
		
		//TODO: fill the rule list with the rule definitions from the table
		
		int ruleN=0;
		boolean emptyFound = false;
		for ( TableRow item : rows )
		{
			++ruleN;
			String packageExpr = item.textPackage.getText();
			String classExpr  = item.textClass.getText();
			String methodExpr  = item.textMethod.getText();
			String comboExpr  = item.ruleTypeCombo.getText();
			
			MatchingRule rule;
			
			System.out.println(packageExpr+" "+classExpr+" "+methodExpr+" "+comboExpr);
			
			if ( comboExpr.length() == 0 && classExpr.length() == 0 && methodExpr.length() == 0 && packageExpr.length() == 0 ){
				continue; //empty line, just ignore it.
			} else if ( comboExpr.length() == 0 ) { //We care only about INLCUDE/EXCLUDE
//			} else if ( comboExpr.length() == 0 || classExpr.length() == 0 || methodExpr.length() == 0 || packageExpr.length() == 0  ){
				throw new ComponentDefinitionShelException("The rule number "+ruleN+" is not complete");
			} else if ( emptyFound ){
				throw new ComponentDefinitionShelException("The rule number "+(ruleN-1)+" is not complete");
			}
			
			
//			if ( emptyFound )
//				continue;
			
			if (comboExpr.equals("INCLUDE"))
			{
				rule=new MatchingRuleInclude(packageExpr,classExpr,methodExpr);
			} else {
				rule=new MatchingRuleExclude(packageExpr,classExpr,methodExpr);
			}
			
			
			matchingRules.add(rule);
			
		}
		
		if ( matchingRules.size() == 0 ){
			throw new ComponentDefinitionShelException("No rule defined");
		}
		
		return matchingRules;
	}
	

	/**
	 * This method create a component using the rules inserted in the table and the name inserted in the text field
	 * 
	 * @return
	 * @throws ComponentDefinitionShelException 
	 */
	public Component getDefinedComponent() throws ComponentDefinitionShelException {
		
		return new Component(nameComponentFilter.getText(), getRules() );
		
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
