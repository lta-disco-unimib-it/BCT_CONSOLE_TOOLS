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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions.IFindActionEditorSupport;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets.ProgramPointsTreeComposite;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProgramPointsVisualizerEditor extends EditorPart implements IFindActionEditorSupport {	
	ProgramPointsTreeComposite tree;
	
	public ProgramPointsVisualizerEditor() {
		super();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// We do not provide save functionality
	}

	@Override
	public void doSaveAs() {
		// We do not provide save as functionality
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.setSite(site);
		super.setInput(input);
		super.setPartName("Program Points Found");
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		tree = new ProgramPointsTreeComposite(parent, SWT.NONE);
	}

	@Override
	public void setFocus() {
		tree.setFocus();
	}
	
	public void setProgramPoints(List<? extends ProgramPoint> programPoints) {
		tree.populateTree(programPoints);
	}

	public void executeFindAction() {
		throw new NotImplementedException();
	}
}
