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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIBuilderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorOpener;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;

public class ConnectedComponentsDialog {

	private Shell shell = null;  
	
//	private Table table = null;
//
//	private Composite parent;

	private List<Set<String>> connectedComponents;

	private HashMap<String, BctModelViolation> violations;

	private Tree tree;

	private MonitoringConfiguration mc;

	public ConnectedComponentsDialog(Shell pshell, MonitoringConfiguration mc, List<Set<String>> list, final HashMap<String, BctModelViolation> violations) {
		this.mc = mc;
		shell = new Shell(pshell, SWT.RESIZE|SWT.DIALOG_TRIM);
		//sShell.setSize(300, 300);
		this.violations=violations;
		connectedComponents = list; 
		
		shell.setLayout (new FillLayout ());
		
		tree = new Tree (shell, SWT.BORDER);
		tree.setHeaderVisible(true);
		
		TreeColumn column = new TreeColumn(tree, SWT.NONE);
		column.setText("CC id");
		column.setWidth(50);
		
		column = new TreeColumn(tree, SWT.NONE);
		column.setText("Violation id");
		column.setWidth(100);
		
		column = new TreeColumn(tree, SWT.NONE);
		column.setText("Model");
		column.setWidth(100);
		
		column = new TreeColumn(tree, SWT.NONE);
		column.setText("Violation");
		column.setWidth(100);
		
		column = new TreeColumn(tree, SWT.NONE);
		column.setText("Extra");
		column.setWidth(100);
		
		Comparator<String> comparator = new Comparator<String>(){

			public int compare(String o1, String o2) {
				String firstNum = o1.substring(o1.lastIndexOf('@')+1);
				String secondNum = o2.substring(o2.lastIndexOf('@')+1);
				
				if ( firstNum.contains(":") ){
					int pos = firstNum.lastIndexOf(':');
					firstNum=firstNum.substring(pos+1);
				}
				
				if ( secondNum.contains(":") ){
					int pos = secondNum.lastIndexOf(':');
					secondNum=secondNum.substring(pos+1);
				}
				
				System.out.println(firstNum);
//				try{
					Integer first = Integer.valueOf(firstNum);
					Integer second = Integer.valueOf(secondNum);
					return first-second;
//				} catch NumberFormatException {
//					return o1.compareTo(o2);
//				}
			}
			
		};
		
		int i = 0;
		for (Set<String> cc : list ) {
			TreeItem root = new TreeItem (tree, 0);
			ArrayList<String> els = new ArrayList<String>();
			els.addAll(cc);
			Collections.sort(els,comparator);
			root.setText (String.valueOf(i++));
			root.setData (els);
			new TreeItem (root, 0);
		}
		
		tree.addListener (SWT.Expand, new Listener () {
			public void handleEvent (final Event event) {
				final TreeItem root = (TreeItem) event.item;
				TreeItem [] items = root.getItems ();
				for (int i= 0; i<items.length; i++) {
					if (items [i].getData () != null) return;
					items [i].dispose ();
				}
				List<String> cc = (List<String>) root.getData ();
				
				if (cc == null) 
					return;
				
				
				
				for (String el : cc) {
					TreeItem item = new TreeItem (root, 0);
					
					BctModelViolation viol = violations.get(el);
					String extra = "";
					if ( viol instanceof BctFSAModelViolation ){
						for ( String state : ((BctFSAModelViolation)viol).getViolationStatesNames() ){
							extra+=state;
						}
					}
					
					item.setText ( new String[]{"",el,viol.getViolatedModel(),viol.getViolation(),extra});
					item.setData(el);
				}
			}
		});
		Point size = tree.computeSize (300, SWT.DEFAULT);
		int width = Math.max (600, size.x);
		int height = Math.max (300, size.y);
		shell.setSize (shell.computeSize (width, height));
		shell.open ();
		
		tree.addMouseListener(new TreeMouseListener());
	}
	
	private class TreeMouseListener extends MouseAdapter {
		@Override
		public void mouseDoubleClick(MouseEvent e) {
			ViolationElement ve;
			TreeItem selection = tree.getSelection()[0];
			Object violationId = selection.getData();
			if ( violationId == null ){
				return;
			}
			
			BctModelViolation violation = violations.get(violationId);
			
			
			if(violation == null) {
				return;
			}
			URI uri = null;

			// TODO: move this elsewhere
			// In IO Violations model ends with :::EXNTER or :::EXIT
			String modelName = violation.getViolatedModel();
			int index = modelName.indexOf(':');
			if ( index < 0 ){
				index = modelName.length();
			}
			String methodName = modelName.substring(0,index);

			Method method = new Method(methodName);
			if(violation instanceof BctFSAModelViolation) {
				FSAModel model = new FSAModel(null, method, null);
				uri = URIBuilderFactory.getBuilder(mc).buildURI(model);
			} else if(violation instanceof BctIOModelViolation) {
				IoModel model = new IoModel(method, null, null);
				uri = URIBuilderFactory.getBuilder(mc).buildURI(model);	
			}
			EditorOpener.openEditor(shell.getDisplay(), uri);

		}
	}

	public void open() {
		shell.open();
	}

	public void close() {
		shell.close();
	}

	public void dispose() {
		shell.dispose();
	}

	

}

