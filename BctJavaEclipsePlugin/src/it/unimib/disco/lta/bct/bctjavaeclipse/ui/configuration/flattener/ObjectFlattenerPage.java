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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.flattener;


import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FlattenerOptions;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class ObjectFlattenerPage extends Composite implements BCTObservable {

	Shell shell;
	private enum fieldsRetriever{All,Inspectors,Getters};
	
	private String[]fieldsRetrieverOptionsText = {"All fields","Inspectable","Getters"};//Options to be shown configuration keys
	private String[]fieldsRetrieverOptionsConfigKeys = {"All","Inspectors","Getters"}; //Corresponding configuration keys
	
	private Text maxDepthText;
	private CCombo fieldsRetrieverText;
	private Text FieldsFilterText;
	private BCTObservaleIncapsulated observable = new BCTObservaleIncapsulated(this);
	
	TableItem[] items;
	TableItem item;
	
	private int numFlattener=0;
	
	List<TableRow> rows = new ArrayList<TableRow>();
	
	
	private class MyTable extends OptionsTable<TableRow>{

		public MyTable(Group classesToIgnore, int lines, int size) {
			super(classesToIgnore, lines, size,new String[]{"Class to Ignore"});
		}


		@Override
		protected TableRow newTableRow() {
			return new TableRow(this);
		}

	}
	
	private class TableRowContent implements OptionsTableRowContent {
		
		private String className;

		public TableRowContent(String className) {
			this.className = className;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}
		
	}
	

	
	
	public class TableRow extends OptionsTableRow<TableRowContent>
	{
		private TableRowContent content = new TableRowContent("");
		private Text classNameText;
		
		
		public TableRow(OptionsTable parentTable)
		{
			super(parentTable);
			
			classNameText = new Text(parentTable.getTable(),SWT.NONE); 
			super.addCell(classNameText);
//			c=new CCombo(parentTable.getTable(),SWT.NONE);
//			c.setItems(new String[]{"uno","due"});
//			super.addCell(c);
			
		}

		public TableRowContent getContent() {
			return content;
		}

		public void setContent(TableRowContent content) {
			System.out.println("SetContent");
			
			this.content.setClassName(content.className);
			
			classNameText.setText(content.className);
//			if ( content.getClassName().equals("uno"))
//				c.select(0);
//			else
//				c.select(1);
//			
			parentTable.update();
			
			
			System.out.println("SetContent END");
		}

		public void cellModified(int cellNumber,String className) {
			System.out.println("Setting content");
			if ( cellNumber == 0 ){
				content.setClassName(className);	
			}
			
			//setItemText(cellNumber, className);
		}

		@Override
		public boolean isEmpty() {
			return content.className.equals("");
		}

	}
	
//	private MonitorConfigurationManager monitoringConfigurationManager;
	private FlattenerOptions flattenerOptions;
	private Button smashAggregationButton;

	private org.eclipse.swt.widgets.List classesToIgnoreList;

	private org.eclipse.swt.widgets.List fieldsFilterList;
	private Button skipVisitedButton;
	
	public ObjectFlattenerPage( Composite _parent, FlattenerOptions flattenerOptions)
	{
		this(_parent,flattenerOptions,SWT.NONE);
	}
	public ObjectFlattenerPage( Composite _parent, FlattenerOptions flattenerOptions,int swt){
		super(_parent,swt);
		
		this.flattenerOptions=flattenerOptions;
        
        Composite parent = this;

        GridLayout l = new GridLayout();
        l.numColumns = 2;
        parent.setLayout(l);
        
        
	    
        
        GridData groupsLayoutData = new GridData(GridData.FILL_HORIZONTAL);
        groupsLayoutData.horizontalSpan =2;
        
        GridData textLayoutData = new GridData(GridData.FILL_HORIZONTAL);
        
        
        /////////////////////////////////////////////////////////////////////////////
		//
		//                FLATTENER OPTIONS GROUP
		//
		/////////////////////////////////////////////////////////////////////////////
        
	    Composite flattenerOptionsGroup=new Composite(parent, SWT.NONE);
	    
	    flattenerOptionsGroup.setLayoutData(groupsLayoutData);
	    
	    GridLayout defaultGroupLayout = new GridLayout();
	    defaultGroupLayout.numColumns = 2;
	    flattenerOptionsGroup.setLayout(defaultGroupLayout);
	    
	    Label label = new Label(flattenerOptionsGroup,SWT.NONE);
	    label.setText("Flattener options");
	    label = new Label(flattenerOptionsGroup,SWT.NONE);
	    
	   
	    
	    label.setToolTipText("BCT records the state of the parameters exchanged in method calls by " +
	    		"recursively traversing fields and recording a string representation of the primitive values found. " +
	    		"\nFollowing options permits to customize the way BCT identifies and records fields.");
	    
	    
        

		Label smashAggregationLabel=new Label(flattenerOptionsGroup, SWT.NULL);
		smashAggregationLabel.setText("Smash Aggregations:");
		
		smashAggregationButton = new Button(flattenerOptionsGroup,SWT.CHECK);
		
		smashAggregationButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				observable.notifyBCTObservers(null);
			}
			
		});
		smashAggregationButton.setToolTipText("Check this box if you want that BCT records aggregations (arrays, list, maps, etc.) ");
		

		Label skipVisitedLabel=new Label(flattenerOptionsGroup, SWT.NULL);
		skipVisitedLabel.setText("Skip objects already visited:");
		
		skipVisitedButton = new Button(flattenerOptionsGroup,SWT.CHECK);
		
		skipVisitedButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				observable.notifyBCTObservers(null);
			}
			
		});
		skipVisitedButton.setToolTipText("Check this box if you want that BCT skips flattening objects already visited in a same object.");
		
		
		Label maxDepthLabel=new Label(flattenerOptionsGroup, SWT.NULL);
		maxDepthLabel.setText("Maximum Depth:");
		
		maxDepthText = new Text(flattenerOptionsGroup, SWT.SINGLE | SWT.BORDER|SWT.FILL);
		maxDepthText.setText("3");
		maxDepthText.setLayoutData(textLayoutData);
		maxDepthText.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				observable.notifyBCTObservers(null);
			}
			
		});
		maxDepthText.setToolTipText("Indicates the maximum depth up to which BCT can inspect an object.");
		
		Label fieldsRetrieverLabel=new Label(flattenerOptionsGroup, SWT.NULL);
		fieldsRetrieverLabel.setText("Flattening mechanism:");	
		
		fieldsRetrieverText = new CCombo(flattenerOptionsGroup, SWT.SINGLE | SWT.BORDER|SWT.FILL);
		fieldsRetrieverText.setItems(fieldsRetrieverOptionsText);
		fieldsRetrieverText.select(0);
		
		fieldsRetrieverText.setLayoutData(textLayoutData);
		fieldsRetrieverText.setToolTipText("Indicates how to extract state information from objects. " +
				"\n\"All fields\" indicates BCT to recursevely record the values of all the fields of the passed objects." +
				"\n\"Getters\" indicates BCT to recursevely invoke getter methods and record the values returned." +
				"\n\"Inspectable\" indicates BCT to recursevely record the values of the fields that have an associated getter method.");
		
		fieldsRetrieverText.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				observable.notifyBCTObservers(null);
			}
			
		});
		

		/////////////////////////////////////////////////////////////////////////////
		//
		//                CLASSES TO IGNORE GROUP
		//
		/////////////////////////////////////////////////////////////////////////////	
		
		Composite classesToIgnore=new Composite(parent, SWT.FILL);
		label = new Label(classesToIgnore, SWT.NONE);
		label.setText("Classes to ignore");
		label = new Label(classesToIgnore, SWT.NONE);
		
		classesToIgnore.setLayoutData(groupsLayoutData);
		

		l = new GridLayout();
		l.numColumns = 2;
		classesToIgnore.setLayout(l);
		
		classesToIgnoreList = new org.eclipse.swt.widgets.List(classesToIgnore,SWT.NONE);
		
		GridData classesToIgnoreListLD = new GridData(GridData.FILL_HORIZONTAL|GridData.FILL_VERTICAL);
		classesToIgnoreListLD.verticalSpan = 5;
		classesToIgnoreListLD.minimumHeight = 200;
		classesToIgnoreListLD.minimumWidth = 200;
		classesToIgnoreList.setLayoutData(classesToIgnoreListLD);
		classesToIgnoreList.setToolTipText("This list shows the names of the classes that must not be flattened by BCT.");
		
		Button classToIgnoreButton=new Button(classesToIgnore,SWT.PUSH);
		classToIgnoreButton.setText("Add");
		classToIgnoreButton.setToolTipText("Add a new class to ignore to the list.");
		
		classToIgnoreButton.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				newClassToIgnoreButtonSelected();
			}

		});

		
		
		Button removeClassToIgnoreButton=new Button(classesToIgnore,SWT.PUSH);
		removeClassToIgnoreButton.setText("Remove");
		removeClassToIgnoreButton.setToolTipText("Remove the selected class from the list.");
		//removeClassToIgnoreButton.setBounds(330,75,60,25);
		removeClassToIgnoreButton.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				removeClassToIgnoreButtonSelected();
			}

		});
		
		
		/////////////////////////////////////////////////////////////////////////////
		//
		//                FIELDS FILTERS GROUP
		//
		/////////////////////////////////////////////////////////////////////////////
		
		Composite fields=new Composite(parent, SWT.NULL);
		label = new Label(fields,SWT.NONE);
		label.setText("Fields filters");
		
		label = new Label(fields,SWT.NONE);
		
		fields.setEnabled(false);
		
		fields.setLayoutData(groupsLayoutData);
		
		fields.setLayout(defaultGroupLayout);
		
		fieldsFilterList = new org.eclipse.swt.widgets.List(fields,SWT.NONE);
		
		fieldsFilterList.setToolTipText("By default BCT records the value of all the fields identified according to the criterion " +
				"indicated in the \"Flattening mechanism\" text. " +
				"\nThis box permits to define additional filters to prevent some fields from being recorded.");
		
		GridData fieldsFilterListLD = new GridData(GridData.FILL_HORIZONTAL|GridData.FILL_VERTICAL);
		fieldsFilterListLD.verticalSpan = 5;
		fieldsFilterList.setLayoutData(fieldsFilterListLD);
		
		Button newModifierButton=new Button(fields,SWT.PUSH);
		newModifierButton.setText("Modifier");
		newModifierButton.setToolTipText("Add a filter based on the modifier type of the field.");
		
		newModifierButton.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				newModifierButtonSelected();
			}

			
			
		});

		Button newFieldRuleButton=new Button(fields,SWT.PUSH);
		newFieldRuleButton.setText("Field");
		newModifierButton.setToolTipText("Add a filter based on the name of the field.");
		
		newFieldRuleButton.addSelectionListener( new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				newFieldRuleButtonSelected();
			}

			
			
		});

		//parent.pack();
	}

	protected void newFieldRuleButtonSelected() {
		// TODO Auto-generated method stub
		
	}
	private void newModifierButtonSelected() {
		//Dialog d = new CreateModifierDialog();
	}
	
	
	private void newClassToIgnoreButtonSelected() {
		InputDialog dialog = new InputDialog(getShell(), "Add class to ignore", "Enter a regular expression that matches the class to ignore", null, null);
		dialog.open();
		String value = dialog.getValue();
		if ( value != null){
			classesToIgnoreList.add(value);
			classesToIgnoreList.update();
			observable.notifyBCTObservers(null);
		}
		
	}
	
	private void removeClassToIgnoreButtonSelected() {
		int[] idxs = classesToIgnoreList.getSelectionIndices();
		if ( idxs.length == 0){
			MessageDialog.openError(getShell(), "Error", "No class to ignore selected");
		} else {
			classesToIgnoreList.remove(idxs);
			observable.notifyBCTObservers(null);
		}
	}
	
		 
	public FlattenerOptions getDefinedFlattenerOptions()
	{
		
		
		String fieldRetriever;
		
		List<String> classesToIgnore=new ArrayList<String>();
		
		//add classes to ignore content

		for ( String classToIgnore : classesToIgnoreList.getItems() ){
			classesToIgnore.add(classToIgnore);
		}
		
			
		boolean smashAggregation = smashAggregationButton.getSelection();
		int maxDepth = Integer.valueOf(maxDepthText.getText());
		
		fieldRetriever = fieldsRetrieverOptionsConfigKeys[ fieldsRetrieverText.getSelectionIndex() ];
		
		
//		if (flattenerOptions ==null)
			flattenerOptions= new FlattenerOptions(smashAggregation,maxDepth,fieldRetriever,classesToIgnore);
			
			flattenerOptions.setSkipObjectsAlreadyVisited(skipVisitedButton.getSelection());
//		else
//		{
//			flattenerOptions.setMaxDepth(maxDepth);
//			flattenerOptions.setSmashAggregation(smashAggregation);
//			flattenerOptions.setFieldRetriever(fieldRetriever);
//			flattenerOptions.setClassToIgnore(classesToIgnore);
//		}

		return flattenerOptions;
		
	}

	public void open() {
		shell.open();
	}

	public void pack() {
		shell.pack();
	}

	public void pack(boolean changed) {
		shell.pack(changed);
	}

	public void close() {
		shell.close();
	}

	public String[] getFieldsRetrieverOptions() {
		return fieldsRetrieverOptionsText;
	}

	public void setFieldsRetrieverOptions(String[] fieldsRetrieverOptions) {
		this.fieldsRetrieverOptionsText = fieldsRetrieverOptions;
	}
	

	
	public void addClassToIgnore(String className)
	{
		classesToIgnoreList.add(className);
		observable.notifyBCTObservers(null);
	}


	public void load(FlattenerOptions newFlattenerOptions) {
		//this.flattenerOptions = newFlattenerOptions;
		smashAggregationButton.setSelection(newFlattenerOptions.isSmashAggregation());
		maxDepthText.setText(""+newFlattenerOptions.getMaxDepth());
		
		String fieldsRetriever = newFlattenerOptions.getFieldRetriever();
		for ( int i = 0; i <  fieldsRetrieverOptionsText.length; ++i ){
			if ( fieldsRetrieverOptionsText[i].equals(fieldsRetriever) ){
				fieldsRetrieverText.select(i);
				break;
			}
		}
		

		for ( String classToIgnore : newFlattenerOptions.getClassToIgnore() ){
			classesToIgnoreList.add(classToIgnore);	
		}
		
		skipVisitedButton.setSelection(newFlattenerOptions.isSkipObjectsAlreadyVisited());

	}


	public void addBCTObserver(BCTObserver l) {
		observable.addBCTObserver(l);
	}
	
		
}
