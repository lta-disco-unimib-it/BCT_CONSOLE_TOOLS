/**
 * 
 */
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components;


import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors.BctMonitoringConfigurationEditor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.sun.prism.paint.Stop;

class CallFilterTableRow implements BCTObservable
{
	public final static int RULE_INCLUDE = 0;
	public final static int RULE_EXCLUDE = 1;
	private BCTObservaleIncapsulated observable;
	
	TableItem items;
	CCombo rule,from;
	private Text textMethod,textClass,textPackage;
	private List<String> componentNames = new ArrayList<String>();
	private CallFilterTable parent;


	public boolean isProperlySet(){
		System.out.println( rule.getSelectionIndex() +" " + textMethod.getText() + " "+textClass.getText()+" "+textPackage.getText());
		return ( rule.getSelectionIndex() > -1
				&& textMethod.getText().length() > 0 
				&& textClass.getText().length() > 0
				&& textPackage.getText().length() > 0 );
	}
	
	public boolean isIncludeSelected(){
		int selection = rule.getSelectionIndex();
		if ( selection < 0 ){
			return false;
		}
		return rule.getItem(selection).equals(ComponentsConfigurationComposite.ruleTypes[0]);
	}
	
	public boolean isExcludeSelected(){
		int selection = rule.getSelectionIndex();
		if ( selection < 0 ){
			return false;
		}
		return rule.getItem(selection).equals(ComponentsConfigurationComposite.ruleTypes[1]);
	}
	
	
	public void removeFromComponentName( String name ){
		componentNames.remove(name);
		from.remove(name);
	}
	
	public void addFromComponentName(String name){
		componentNames.add(name);
		from.add(name);
	}
	
	public boolean isFromComponentSelected(String name){
		int selIdx = from.getSelectionIndex();
		if ( selIdx < 0 ){
			return false;
		}
		return from.getItem(selIdx).equals(name);
	}
	
	public CallFilterTableRow(final CallFilterTable parent, Table callFilterTable, List<String> componentNames) 
	{
		
		this.parent = parent;
		this.componentNames.addAll( componentNames );
		items =new TableItem(callFilterTable, SWT.NONE);
		
		observable = new BCTObservaleIncapsulated(this);
		
		
		
		TableEditor editorPackage=new TableEditor(callFilterTable);
		
		
		
		textPackage=new Text(callFilterTable,SWT.NONE);
		textPackage.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				cellChanged();
			}
			
		});
		editorPackage.grabHorizontal = true;
		
		if ( ! BctMonitoringConfigurationEditor.STOP ){
			editorPackage.setEditor(textPackage,items,0);
		}
		
		
		TableEditor editorClass=new TableEditor(callFilterTable);
		textClass=new Text(callFilterTable,SWT.NONE);
		textClass.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				cellChanged();
			}
			
		});
		editorClass.grabHorizontal = true;
		
		if ( ! BctMonitoringConfigurationEditor.STOP ){
			editorClass.setEditor(textClass,items,1);
		}
		
		TableEditor editorMethod=new TableEditor(callFilterTable);
		textMethod=new Text(callFilterTable,SWT.NONE);
		textMethod.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				cellChanged();
			}
			
		});
		editorMethod.grabHorizontal = true;
		if ( ! BctMonitoringConfigurationEditor.STOP ){
		editorMethod.setEditor(textMethod,items,2);
		}
		
		TableEditor editorFrom = new TableEditor(callFilterTable);    
		from = new CCombo(callFilterTable, SWT.NONE);
		String[] components = componentNames.toArray(new String[0]);
		from.setItems(components);		
		from.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				cellChanged();
			}
			
		});
		editorFrom.grabHorizontal = true;
		if ( ! BctMonitoringConfigurationEditor.STOP ){
		editorFrom.setEditor(from, items, 3);
		}
		
		TableEditor editorRules = new TableEditor(callFilterTable);    
		rule = new CCombo(callFilterTable, SWT.NONE);
		rule.setItems(ComponentsConfigurationComposite.ruleTypes);	        
		rule.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				cellChanged();
			}
			
		});

		editorRules.grabHorizontal = true;
		
		if ( ! BctMonitoringConfigurationEditor.STOP ){
		editorRules.setEditor(rule, items, 4);
		}
		
		
		
		rule.addTraverseListener(new TraverseListener() {
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


	

	private CallFilterTableRow getRow() {
		return this;
	}

	public String getFromComponentName() {
		int sel = from.getSelectionIndex();
		if ( sel < 0 )
			return null;
		
		return from.getItem(sel);
	}

	public void setFrom(CCombo from) {
		this.from = from;
	}



//	void setValues( String packageRule, String classRule, String methodRule, RuleTypes ruleType ){
//	textPackage.setText(packageRule);
//	textClass.setText(classRule);
//	textMethod.setText(methodRule);
//	int index;
//	if ( ruleType.equals(RuleTypes.INCLUDE)){
//	index = 0;
//	} else {
//	index = 1;
//	}

//	combo.select(index);
//	}

	public String getClassExpression() {
		return textClass.getText();
	}
	
	public String getMethodExpression() {
		return textMethod.getText();
	}
	
	public String getPackageExpression() {
		return textPackage.getText();
	}

	public void setValues(String packageExpr, String classExpr,
			String methodExpr, String componentName, int ruleType) {
		
		textPackage.setText(packageExpr);
		textClass.setText(classExpr);
		textMethod.setText(methodExpr);
		rule.select(ruleType);
		String items[] = from.getItems();
		for ( int i = 0; i < items.length; ++i ){
			if ( items[i].equals(componentName) ){
				from.select(i);
				break;
			}
		}
		
	}

	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}

	public void cellChanged(){
		observable.notifyBCTObservers(null);
	}

	public void reset(ArrayList<String> componentNames2) {
		componentNames.clear();
		componentNames.addAll(componentNames2);
		textPackage.setText("");
		textClass.setText("");
		textMethod.setText("");
		rule.setText("");
	}

	public void addLastCellTraverseListener(TraverseListener listener) {
		rule.addTraverseListener(listener);
		
	}
}