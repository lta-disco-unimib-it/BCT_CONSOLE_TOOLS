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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class TableItemsFindJob extends Job {

	private Table table;
	private String regex;
	private ArrayList<Integer> indicesFound;
	
	public TableItemsFindJob(String name, String regex, Table table) {
		super(name);
		this.regex = regex;
		this.table = table;
	}

	
//	protected IStatus run(final IProgressMonitor monitor) {
//		// Get table size
//		final AtomicInteger tableSize = new AtomicInteger();
//		Display.getDefault().syncExec(new Runnable() {
//			public void run() {
//				tableSize.set(table.getItems().length);
//			}
//		});
//		
//		// Get matching items indices
//		final List<Integer> selectedIndices = new ArrayList<Integer>();
//		monitor.beginTask("Find all matching items", tableSize.get());
//		final AtomicInteger index = new AtomicInteger(0);
//		for (int i = 0; i < tableSize.get() && !monitor.isCanceled(); i++) {
//			Display.getDefault().syncExec(new Runnable() {
//				public void run() {
//					int itemIndex = index.getAndIncrement();
//					TableItem item = table.getItem(itemIndex);
//					if (item.getText(1).matches(regex)) {
//						selectedIndices.add(itemIndex);
//					}
//				}
//			});
//			monitor.worked(1);
//		}
		
	public class MyIntRes {
		int x;
		
		public MyIntRes(){
			
		}
		
		public void setInt( int x ){
			this.x = x;
		}

		public int getInt(){
			return x;
		}
	}
	
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		// Get table size
		final MyIntRes tableSize = new MyIntRes();

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				tableSize.setInt( table.getItems().length );

			}
		});

		// Get matching items indices

		indicesFound = new ArrayList<Integer>();
		monitor.beginTask("Find all matching items", tableSize.getInt());
		
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				int tableSizeInt = tableSize.getInt();
				System.out.println(tableSizeInt);
				int start = table.getSelectionIndex()+1;
				if ( start < 0 || start > tableSizeInt){
					start = 0;
				}
				for (int i = start; i < tableSizeInt && !monitor.isCanceled(); i++) {

					TableItem item = table.getItem(i);
					if (item.getText(1).matches(regex)) {
						indicesFound.add(i);
						break;
					}
				}
			}
		});

		monitor.worked(1);
	
		
		// Select matching items
		final int[] indices = new int[indicesFound.size()];
		for (int i = 0; i < indicesFound.size(); i++) {
			indices[i] = indicesFound.get(i);
		}
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				table.deselectAll();
				table.select(indices);
				if ( indicesFound.size() > 0 ){
					table.setTopIndex(indicesFound.get(0));	
				}
				
			}
		});
		
		monitor.done();
		if (!monitor.isCanceled()) {
			return Status.OK_STATUS;	
		} else {
			return Status.CANCEL_STATUS;
		}	
	}

	public List<Integer> getIndicesFound() {
		return indicesFound;
	}

	
	
}
