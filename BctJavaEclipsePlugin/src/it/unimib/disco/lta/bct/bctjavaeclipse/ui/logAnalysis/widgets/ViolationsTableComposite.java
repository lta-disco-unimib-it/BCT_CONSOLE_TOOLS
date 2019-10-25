package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorOpener;

import java.awt.Dialog;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import modelsViolations.BctModelViolation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class ViolationsTableComposite extends Composite {

	private static final String ELEMENTS = "Elements";
	private static final String RESOURCE = "Resource";
	private static final String OPEN = "Open";
	private static final String SIZE = "Size";
	private Table table;
	private TableColumn elementsColumn;
	private TableColumn resourceColumn;
	private TableColumn sizeColumn;
	private TableViewer tableViewer;
	//private TableViewer tw;
	
	private String[] columnNames = new String[] { 
			ELEMENTS, 
			RESOURCE,
			SIZE,
			OPEN
			};
	private TableColumn openColumn;
	private TableColumn showColumn;
	private HashMap<String,BctModelViolation> violations = new HashMap<String, BctModelViolation>();
	private MonitoringConfiguration monitoringConfiguration;
	
	
	public ViolationsTableComposite(Composite parent, int style) {
		super(parent, style);
		
//		GridLayout g = new GridLayout();
//		g.numColumns = 1;
//		this.setLayout(g);
		
		//tw = new TableViewer(this,SWT.NONE);
		Label l = new Label(this,SWT.NONE);
		l.setText("Table shows the anomaly graphs computed. Best result is highlighted");
		
		createTable();
	    
	}



	private void createTable() {
		
		table = new Table(this, SWT.BORDER | SWT.MULTI);
//		table.setLayout(new FillLayout());
		
		table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    
	    //table.setSize(500, 150);
	    table.setBounds(15,15,575,300);
		
	    elementsColumn = new TableColumn(table, SWT.NONE, 0);
	    elementsColumn.setText(ELEMENTS);
	    elementsColumn.setWidth(100);
	    
	    resourceColumn = new TableColumn(table, SWT.NONE, 1);
	    resourceColumn.setText("Resource");
	    resourceColumn.setWidth(200);
	    
	    sizeColumn = new TableColumn(table, SWT.NONE, 2);
	    sizeColumn.setText("Size");
	    sizeColumn.setWidth(50);
	    
	    showColumn = new TableColumn(table, SWT.NONE, 3);
	    showColumn.setText("Connected Components");
	    showColumn.setWidth(100);
	    
	    openColumn = new TableColumn(table, SWT.NONE, 4);
	    openColumn.setText("Anomaly Graph");
	    openColumn.setWidth(100);
		
	}

	public void setElementsColumnTitle(String title){
		elementsColumn.setText(title);
	}
	
	public void setResourceColumnTitle(String title){
		resourceColumn.setText(title);
	}
	
	public void setSizeColumnTitle(String title){
		sizeColumn.setText(title);
	}
	
	public void addElement( String element, String resource, List<Set<String>> list ){
		addElement(element, resource, list, false);
	}
	
	public void addElement( String element, final String resource, List<Set<String>> list, boolean highlight ){
		
		if (list == null ){
			list = new ArrayList<Set<String>>();
		}
		
		TableItem items = new TableItem(table, SWT.NONE);
	    
	    
		TableEditor editor=new TableEditor(table);
		Text text = new Text(table,SWT.NONE);
		text.setText(element);
		text.setEditable(false);
		
		if ( highlight ){
			text.setBackground(new Color(getDisplay(),255,85,0));
		}
		
		editor.grabHorizontal = true;
		editor.setEditor(text,items,0);
		
		
		editor = new TableEditor(table);
		text = new Text(table,SWT.NONE);
		text.setText(resource);
		text.setEditable(false);
		
		if ( highlight ){
			text.setBackground(new Color(getDisplay(),255,85,0));
		}
		
		editor.grabHorizontal = true;
		editor.setEditor(text,items,1);
		
		editor = new TableEditor(table);
		text = new Text(table,SWT.NONE);
		text.setText(String.valueOf(list.size()));
		text.setEditable(false);
		
		if ( highlight ){
			text.setBackground(new Color(getDisplay(),255,85,0));
		}
		
		editor.grabHorizontal = true;
		editor.setEditor(text,items,2);
		
		editor = new TableEditor(table);
		Button showButton = new Button(table, SWT.PUSH);
		showButton.setSize(20, 20);
		showButton.setText("show");
		final List<Set<String>> resList = list;
		showButton.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				openConnectedComponentsDialog( resList );
			}
			
			

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		editor.grabHorizontal = true;
		editor.setEditor(showButton,items,3);
		
		editor = new TableEditor(table);
		Button openButton = new Button(table, SWT.PUSH);
		openButton.setSize(20, 20);
		openButton.setText("open");
		openButton.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			public void widgetSelected(SelectionEvent e) {
				//IProgressMonitor monitor = new NullProgressMonitor();
				
				
				getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						IWorkbenchPage page =
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//						try {
							IFile file;
							
							//first look in the workspace
							file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(resource));
							
							//if it is not in the workspace maybe it is because its an absolute path
							if ( file == null || ( ! file.exists() ) ){
								file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(resource));
							}
							
							//last chanche, maybe was an absolute path moved
							if ( file == null ){
								//identify the path relative to workspace
								int start =resource.indexOf("BCT_DATA");
								
								
								if ( start >= 0 ){
									String relativePath = "/"+resource.substring(start);
									file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(relativePath));
								}
								
								
							}
							URI luri = file.getLocationURI();
							EditorOpener.openEditor(getDisplay(), file.getLocationURI());
							//IDE.openEditor(page, file, true);
//						} catch (PartInitException e) {
//							Logger.getInstance().log(e);
//						}
					}
				});
				
			}
			
		});
		editor.grabHorizontal = true;
		editor.setEditor(openButton,items,4);
		
		
		
		
//		editor.grabHorizontal = true;
//		editor.setEditor(text,items,0);
	}
	
	
	private void openConnectedComponentsDialog(List<Set<String>> list) {
		
		Collections.sort(list,new Comparator<Set<String>>() {

			public int compare(Set<String> o1, Set<String> o2) {
				return o2.size()-o1.size();
			}
			
		});
		
		ConnectedComponentsDialog dialog = new ConnectedComponentsDialog(this.getShell(),monitoringConfiguration,list,violations);
		dialog.open();
	}
	
	

	public void removeAll() {
		table.clearAll();
		table.removeAll();
		table.redraw();
	}
	
	public void setTitles(String element, String resource, String size, String open ){
		elementsColumn.setText(element);
		resourceColumn.setText(resource);
		sizeColumn.setText(size);
		openColumn.setText(open);
	}

	public void highLightElement(int toHighLight) {
		if ( toHighLight >= 0 ){
			table.getItem(toHighLight).setGrayed(true);
			
			table.getItem(toHighLight).setBackground(0, new Color(getDisplay(),255,85,0) );
		}
		
	}



	public void setViolations(List<BctModelViolation> violations) {
		this.violations = new HashMap<String, BctModelViolation>();
		if ( violations == null ){
			return;
		}
		for ( BctModelViolation violation : violations ){
			System.out.println("VIOLATION "+violation.getId()+" "+violation.getViolatedModel()+" "+violation.getViolation());
			this.violations.put(violation.getId(), violation);
		}
	}



	public void setMonitoringConfiguration(MonitoringConfiguration mc) {
		monitoringConfiguration = mc;
	}



	public MonitoringConfiguration getMonitoringConfiguration() {
		return monitoringConfiguration;
	}



	
	
}
