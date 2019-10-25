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


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public abstract class OptionsTable <T extends OptionsTableRow>{
	private ArrayList<T> rows = new ArrayList<T>();
	protected Table table;
	private int plainRow;
	
	
	/**
	 * Create a new table with the specified number of lines and the passed column names.
	 * By default each column has the same width.
	 *  
	 * @param classesToIgnore
	 * @param size
	 * @param columns
	 */
	public OptionsTable( Group classesToIgnore, int lines, int width, String columns[] ) {
		
		table =new Table(classesToIgnore, SWT.VIRTUAL | SWT.BORDER );//| SWT.H_SCROLL);
		
//		
		
		int colSize = width/columns.length;
		
		
		for ( String columnName : columns ){
			TableColumn classToIgnoreColumn = new TableColumn(getTable(), SWT.NONE, 0);
			classToIgnoreColumn.setText(columnName);
			classToIgnoreColumn.setWidth(colSize);
		}
		
		//table.addTraverseListener(new OptionsTableTraverseListener());
		for (int i = 0; i < lines; i++) 
		{
			T row = newTableRow();
			rows.add(row);
		}
		
		update();
		
	}

	protected abstract T newTableRow();


	
	public void addEmptyRow(){
		T newRow = newTableRow();
		rows.add(newRow);
	}
	
	/**
	 * Add the content to a empty row of a table, if all rows are full create a new row.
	 * 
	 * @param content
	 */
	public void addRow(OptionsTableRowContent content){
		System.out.println("OptionsTable.addRow ");
		T free = getLastFreeRaw();
		if ( free == null ){
			free = newTableRow();
			rows.add(free);
		}
		
		free.setContent(content);
		
		plainRow++;
		update();
	}
	
	/**
	 * Add all passed contents to the table, if there are not so much rows available create them.
	 * 
	 * @param rows
	 * @return
	 */
	public boolean addAllRows(Collection<OptionsTableRowContent> rows) {
		for ( OptionsTableRowContent row : rows ){
			addRow(row);
		}
		return true;
	}

	public boolean contains(Object arg0) {
		return rows.contains(arg0);
	}

	public boolean isEmpty() {
		return rows.isEmpty();
	}

	public Iterator<T> iterator() {
		return rows.iterator();
	}

	public T remove(int arg0) {
		return rows.remove(arg0);
	}

	public boolean remove(Object arg0) {
		return rows.remove(arg0);
	}

	public void setBounds(Rectangle rect) {
		table.setBounds(rect);
	}
	
	

	public void setHeaderVisible(boolean show) {
		table.setHeaderVisible(show);
	}

	public void setLinesVisible(boolean show) {
		table.setLinesVisible(show);
	}

	public Table getTable() {
		return table;
	}

	public T getLastFreeRaw() {
		for ( T row : rows ){
			if ( row.isEmpty() ){
				return row;
			}
		}
//		if ( rows.size() > 0 && plainRow < rows.size()  ){
//			return rows.get(plainRow);
//		}
		return null;
	}
	
	public void update() {
		table.redraw();
		table.update();
	}
	
	public void setBounds(int i, int j, int k, int l) {
		table.setBounds(i, j, k, l);
	}

	/**
	 * Returns all the non empty rows of the table
	 * 
	 * @return
	 */
	public List<T> getRows(){
		ArrayList<T> result = new ArrayList<T>();
		for ( T row : rows){
			if ( ! row.isEmpty() ){
				result.add(row);
			}
		}
		return result;
	}
}
