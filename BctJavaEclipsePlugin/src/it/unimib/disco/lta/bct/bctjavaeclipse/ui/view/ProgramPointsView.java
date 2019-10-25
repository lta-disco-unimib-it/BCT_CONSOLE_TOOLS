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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.InteractionRawTracesEditor;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets.MethodCallItem;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets.ProgramPointsTreeComposite;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import sun.misc.GC.LatencyRequest;

public class ProgramPointsView extends ViewPart implements ISelectionListener {

	private ProgramPointsTreeComposite tree;
	private InteractionRawTracesEditor lastEditorSelected;
	private MonitoringConfiguration mc;

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		// TODO Auto-generated method stub
		super.init(site, memento);
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		// TODO Auto-generated method stub
		super.init(site);
	}

	@Override
	public void createPartControl(Composite arg0) {
		getSite().getPage().addSelectionListener(this);  
		tree = new ProgramPointsTreeComposite(arg0, SWT.NONE);
	}

	@Override
	public void setFocus() {
		
	}

	@Override
	public void selectionChanged(IWorkbenchPart arg0, ISelection arg1) {
		// TODO Auto-generated method stub
//		System.out.println(arg0.getClass().getCanonicalName());
		if ( arg0 != null ){
			if ( arg0 instanceof InteractionRawTracesEditor ){
				if ( arg0 != lastEditorSelected ){
					lastEditorSelected = (InteractionRawTracesEditor)arg0;
					mc = lastEditorSelected.getMonitoringCOnfiguration();
					tree.setMonitoringConfiguration( mc );
				}
			}
		}
		if ( arg1 instanceof StructuredSelection ){
			Iterator it = ((StructuredSelection)arg1).iterator();
			
			List<ProgramPoint> callPoints = new ArrayList<ProgramPoint>();
			
			while ( it.hasNext() ){
				Object el = it.next();
				
				if (el instanceof MethodCallItem ){
					MethodCallItem callItem = (MethodCallItem) el;
					
					
					ProgramPoint enter = callItem.getMethodCall().getCorrespondingProgramPoint();
					if ( enter != null ){
						callPoints.add(enter);
					}
					
					MethodCallPoint exitMethodCall = callItem.getMethodCallExit();
					if ( exitMethodCall != null ){
						ProgramPoint exit = exitMethodCall.getCorrespondingProgramPoint();
						if ( exit !=null ){
							callPoints.add(exit);
						}
					}
				} else if (el instanceof MethodCallPoint ){
					MethodCallPoint callPoint = (MethodCallPoint) el;
					ProgramPoint enter = callPoint.getCorrespondingProgramPoint();
					if ( enter != null ){
						callPoints.add(enter);
					}
				}

			}
		
			tree.populateTree(callPoints);
			tree.expandAll();
		}
	}

}
