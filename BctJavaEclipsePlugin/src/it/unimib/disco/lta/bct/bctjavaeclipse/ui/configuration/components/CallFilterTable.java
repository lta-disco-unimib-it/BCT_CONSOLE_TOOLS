package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;


import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilter;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterComponent;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CallFilterGlobal;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors.BctMonitoringConfigurationEditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import util.componentsDeclaration.Component;
import util.componentsDeclaration.MatchingRule;
import util.componentsDeclaration.MatchingRuleExclude;
import util.componentsDeclaration.MatchingRuleInclude;

public class CallFilterTable extends Composite implements BCTObservable, BCTObserver {
	@Override
	public void setBounds(int x, int y, int width, int height) {
		table.setBounds(0,0,width,height);
		super.setBounds(x, y, width, height);
	}

	@Override
	public void setBounds(Rectangle rect) {
		setBounds(rect.x, rect.y, rect.width, rect.height);
	}

	private ArrayList<CallFilterTableRow> rows = new ArrayList<CallFilterTableRow>();
	private Table table;
	private ComponentsConfigurationManager ccm;
	private int rowsNumber = 5;
	private List<String> componentsNames;
	private BCTObservaleIncapsulated observable;
	
	
	
	public CallFilterTable(Composite parent,List<String> componentsNames, ComponentsConfigurationManager cm) {
		super(parent,SWT.NONE);
		
		// TODO Auto-generated constructor stub
		this.ccm = cm;
		this.componentsNames = componentsNames;
	    table = new Table(this, SWT.BORDER | SWT.MULTI);
//	    {
//	    	int counter;
//			private Rectangle last;
//			
//			@Override
//			public Rectangle getClientArea() {
//				if ( counter > 5 ){
//					return last;
//				}
//				counter++;
//				last = super.getClientArea();
//				return last;
//			}
//	    	
//	    };

	    table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    //table.setSize(500, 150);
	    //table.setBounds(15,15,540,150);
		table.setToolTipText("This table indicates the environment methods that must not be monitored by BTC." +
				"\nWe consider environment methods the methods that do not belong to any defined components but " +
				"which are invoked within the defined components." +
				"\nYou tipically do not want to monitor library methods that are invoked often or that receive big objects." +
				"\nWe provide a predefined set of methods to not monitor." +
				"In certain cases it is useful to filter out also methods belonging to package java.io.");
	    
	    TableColumn packageName = new TableColumn(table, SWT.NONE, 0);
	    packageName.setText("Package");
	    packageName.setWidth(100);
	    packageName.setToolTipText("Regular expression that matches the package of the methods to filter.");
	    
	    TableColumn className = new TableColumn(table, SWT.NONE, 1);
	    className.setText("Class");
	    className.setWidth(100);
	    className.setToolTipText("Regular expression that matches the class of the methods to filter.");
	    
	    TableColumn methodName = new TableColumn(table, SWT.NONE, 2);
	    methodName.setText("Method");
	    methodName.setWidth(100);
	    methodName.setToolTipText("Regular expression that matches the name of the methods to filter.");
	    
	    TableColumn from = new TableColumn(table, SWT.NONE, 3);
	    from.setText("From");
	    from.setWidth(100);
	    from.setToolTipText("Indicates the invocation context to which this rule applies, i.e. the components from which the filtered method is invoked. " +
	    		"\nFor now we permit only a coarse granularity: you can filter out only invocations made from any component.");
	    
	    
	    
	    TableColumn rules = new TableColumn(table, SWT.NONE, 4);
	    rules.setText("Rules");
	    rules.setWidth(100);
	    rules.setToolTipText("Set INCLUDE if the regular expressions in this line match methods that must be trced by BCT." +
				"\n\nSet EXCLUDE if the regular expressions in this line match methods that must not be traced by BCT." +
				"\n\nNotice that filters are run from top to bottom. " +
				"\nA method that matches an INCLUDE rule " +
				"is considered part of the component even if it matches following EXCLUDE rules." +
				"\nA class that matches an EXCLUDE rule is considered not part of a component even if it matches following INCLUDE rules.");
	    
	    
	    
	    
	    createRows();
	    
	    
	    
	    
	    
	    observable = new BCTObservaleIncapsulated(this);
	    
	    Rectangle b = getBounds();
	    table.setBounds(0, 0, b.width, b.height);
	    

	}
	
	public void addComponentName(String name){
		for ( int i = 0; i < rows.size(); ++i ){
			CallFilterTableRow row = rows.get(i);
			row.addFromComponentName(name);
		}	
		table.update();
	}
	
	public void removeComponentName(String name){
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for ( int i = 0; i < rows.size(); ++i ){
			CallFilterTableRow row = rows.get(i);
			if ( row.isFromComponentSelected(name) ){
				list.add(i);
			} else {
				row.removeFromComponentName(name);
			}
		}
		
		int[] toRemove = new int[list.size()];
		for ( int i = 0; i < list.size(); ++i ){
			toRemove[i] = list.get(i);
		}
		table.remove(toRemove);
		table.update();
	}

	public List<CallFilter> getCallFilters() {
		
		HashMap<String,CallFilter> callFilters = new HashMap<String, CallFilter>();
		
		for ( CallFilterTableRow row : rows ){
			if ( row.isProperlySet() ){
				String classExp = row.getClassExpression();
				String packExp = row.getPackageExpression();
				String methExp = row.getMethodExpression();
				
				//build the rule
				MatchingRule rule;
				if ( row.isIncludeSelected() ){
					rule = new MatchingRuleInclude(packExp,classExp,methExp);
				} else {
					rule = new MatchingRuleExclude(packExp,classExp,methExp);
				}
				
				//Retrieve the callFilter
				String from = row.getFromComponentName();
				if ( from != null ){
					CallFilter callFilter = callFilters.get(from);
					if ( callFilter == null ){
						if ( from.equals("*") ){
							callFilter = new CallFilterGlobal();
						} else {
							Component component = ccm.getComponent(from);
							System.out.println("Component "+component);
							callFilter = new CallFilterComponent(component);
						}
						callFilters.put(from, callFilter);
					}
					callFilter.addRule(rule);
				}
			}
			
			
		}
		
		ArrayList<CallFilter> cf = new ArrayList<CallFilter>(callFilters.size());
		cf.addAll(callFilters.values());
		
		return cf;
	}

	public void loadCallFilters(Collection<CallFilter> filters) {

		
		int i = 0;
		for( CallFilter f : filters  ){
			
			 
			String componentName;
			if ( f.isCallFilterComponent() ){
				CallFilterComponent cc = (CallFilterComponent) f;
				
				componentName = "";//cc.getCallFilterComponent().getName();
				
			} else {
				componentName = "*";
			}
			
			for ( MatchingRule callToR : f.getCallToRules() ){
				int ruleType;
				if ( callToR instanceof MatchingRuleInclude ){
					ruleType = CallFilterTableRow.RULE_INCLUDE;
				} else {
					ruleType = CallFilterTableRow.RULE_EXCLUDE;
				}
				
				if ( i >= rows.size() ){
					addRow();
				}
				
				rows.get(i).setValues(callToR.getPackageExpr(),callToR.getClassExpr(),callToR.getMethodExpr(),componentName,ruleType);
				++i;
			}
			
			
		}
		
		
	}

	private void createRows() {
		for(int i=0;i<rowsNumber;i++)
		{
			addRow();	  
		}
	}
	
	private void addRow(){
		CallFilterTableRow row = new CallFilterTableRow(this,table,componentsNames);	
		rows.add(row);

		row.addBCTObserver(this);
	}

	
	
	public void clear() {
		table.removeAll();
	}

	public void reset(ArrayList<String> componentNames) {
		for ( CallFilterTableRow row : rows ){
			row.reset(componentNames);
		}
	}

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}

	public void bctObservableUpdate(Object modifiedObject, Object message) {
		observable.notifyBCTObservers(message);
	}

	public Table getTable() {
		return table;
	}

	public void rowTraversed(CallFilterTableRow row) {
		if ( row == rows.get(rows.size()-1)){
			addRow();
		}
	}

}
