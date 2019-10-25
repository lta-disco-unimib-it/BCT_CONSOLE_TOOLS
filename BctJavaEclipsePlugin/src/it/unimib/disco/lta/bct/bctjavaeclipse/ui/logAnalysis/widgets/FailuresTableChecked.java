package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservable;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObservaleIncapsulated;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.BCTObserver;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.FilterCommand.FilteringAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class FailuresTableChecked extends Composite implements BCTObservable, Listener, SelectionListener, MouseListener, FocusListener {

	private Table table;
	private BCTObservaleIncapsulated observable;
	
	private FilteringAttribute filteringAttribute;
	private TableItem selected;
	
	public FilteringAttribute getFilteringAttribute() {
		return filteringAttribute;
	}
	
	public void setFilteringAttribute(FilteringAttribute filteringAttribute) {
		this.filteringAttribute = filteringAttribute;
	}
	
	FailuresTableChecked(Composite parent, int v, String[] ids) {
		super(parent,v);
		observable = new BCTObservaleIncapsulated(this);
		
		table = new Table(this, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL);
		table.addSelectionListener(this);
		table.addMouseListener(this);
		table.addFocusListener(this);
		
		for (int i = 0; i < ids.length; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(ids[i]);
			item.addListener(SWT.CHECK, this);
			item.addListener(SWT.SELECTED, this);
			item.addListener(SWT.MouseDoubleClick, this);
		}
		//table.setBounds(10, 10, 120, 120);
		
		
	}
	public void addItems( Collection<String> ids ){
		
		for (String itemName : ids) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(itemName);
		}
		
	}
	
	public List<String> getSelectedIds(){
		ArrayList<String> ids = new ArrayList<String>();
		
		for( TableItem item : table.getItems() ){
			if ( item.getChecked() ){
				ids.add(item.getText());
			}
		}
		
		return ids;
	}

	public void removeAll() {
		table.removeAll();
	}
	public ArrayList<String> getUnSelectedIds() {
		ArrayList<String> ids = new ArrayList<String>();
		
		for( TableItem item : table.getItems() ){
			if ( ! item.getChecked() ){
				ids.add(item.getText());
			}
		}
		
		return ids;

	}
	public ArrayList<String> getTableItems() {
		ArrayList<String> ids = new ArrayList<String>();
		for( TableItem item : table.getItems()  ){
			ids.add(item.getText());
		}
		return ids;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		table.setBounds(0, 0, width-5, height-5);
	}
	
	public void setChecked(String failingPID) {
		
		for( TableItem item : table.getItems()  ){
			if ( item.getText().equals(failingPID)){
				item.setChecked(true);
				break;
			}
		}
	}


	public void unCheckAll(){
		for( TableItem item : table.getItems()  ){
			item.setChecked(false);
		}
	}
	
	public void checkAll(Set<String> toSelect) {
		for( TableItem item : table.getItems()  ){
			System.out.println("Checking "+item.getText() + " "+toSelect.contains( item.getText()));
			if ( toSelect.contains( item.getText() ) ){
				item.setChecked(true);
			}
		}
	}
	public void addBCTObserver(BCTObserver bctObserver) {
		observable.addBCTObserver(bctObserver);
	}
	public void handleEvent(Event e) {
		
	}
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}
	public void widgetSelected(SelectionEvent e) {
		if ( e.detail == SWT.CHECK ) {
			observable.notifyBCTObservers(null);
		}
		
	}
	public void mouseDoubleClick(MouseEvent e) {
		
		
	}
	public void mouseDown(MouseEvent e) {
		
		TableItem item = table.getItem(new Point(e.x,e.y));
		if ( item != null ) {
			if ( selected == item ){
				selected = null;
				observable.notifyBCTObservers(new RemoveFilterCommand());
			} else {
				selected = item;
				observable.notifyBCTObservers(new FilterCommand(item.getText(), filteringAttribute ));
			}
		}
	}
	public void mouseUp(MouseEvent e) {
		
	}

	public void focusGained(FocusEvent e) {
		
	}

	public void focusLost(FocusEvent e) {
		//observable.notifyBCTObservers(new RemoveFilterCommand());
	}


}
