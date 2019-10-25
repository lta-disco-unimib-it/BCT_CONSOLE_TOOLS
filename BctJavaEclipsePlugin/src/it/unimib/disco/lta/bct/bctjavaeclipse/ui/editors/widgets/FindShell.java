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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets;

import java.util.ArrayList;
import java.util.List;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions.TableItemsFindJob;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class FindShell {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="373,76"
	
	
	public void dispose() {
		sShell.dispose();
	}

	public void open() {
		sShell.open();
	}

	public void pack() {
		sShell.pack();
	}

	public void setVisible(boolean visible) {
		sShell.setVisible(visible);
	}

	private Label label = null;
	private Label label1 = null;
	private Text text = null;
	private Button button = null;
	private Label resultMessage = null;
	private Shell parentShell;
	private Table table;
	private String title;

	
	
	public FindShell(Shell shell, Table table, String title) {
		this.parentShell = shell;
		this.table = table;
		this.title = title;
		
		createSShell();
	}

	/**
	 * This method initializes sShell	
	 *
	 */
	private void createSShell() {
		
		GridData gridDataMsg = new GridData();
		gridDataMsg.horizontalSpan = 2;
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		sShell = new Shell(parentShell);
		
		sShell.setText(title);
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(378, 168));
		
		
		
		label1 = new Label(sShell, SWT.NONE);
		label1.setText("Regular Expression to find:");
		text = new Text(sShell, SWT.BORDER);
		
		
		Label filler3 = new Label(sShell, SWT.NONE);
		
		button = new Button(sShell, SWT.NONE);
		button.setText("Find Next");
		button.addMouseListener(new MouseListener() {
			
			public void mouseUp(MouseEvent e) {
				
			}
			
			public void mouseDown(MouseEvent e) {
				runFind();
			}
			
			

			public void mouseDoubleClick(MouseEvent e) {
				
			}
		});
		resultMessage = new Label(sShell, SWT.NONE);
		resultMessage.setText("");
		resultMessage.setLayoutData(gridDataMsg);
	}
	
	private void setResultMessage(String msg ){
		resultMessage.setText(msg);
	}

	private void runFind() {
		String regex = getRegularExpression();
		
		if ( regex.length() == 0 ){
			setResultMessage("You need to type a regular expression to find");
		}
			
		final TableItemsFindJob findTableItems = new TableItemsFindJob("Find matching items", regex, table);
		findTableItems.setUser(true);
		findTableItems.schedule();
		findTableItems.addJobChangeListener(new IJobChangeListener() {
			
			public void sleeping(IJobChangeEvent event) {
				
			}
			
			public void scheduled(IJobChangeEvent event) {
				
			}
			
			public void running(IJobChangeEvent event) {
				
			}
			
			public void done(IJobChangeEvent event) {
				List<Integer> indices = findTableItems.getIndicesFound();
				if ( indices == null || indices.size() == 0 ){
					setResultMessage("Nothing found");
				}
			}
			
			public void awake(IJobChangeEvent event) {
				
			}
			
			public void aboutToRun(IJobChangeEvent event) {
				
			}
		});
	}

	private String getRegularExpression() {
		return text.getText();
	}
	
	
}
