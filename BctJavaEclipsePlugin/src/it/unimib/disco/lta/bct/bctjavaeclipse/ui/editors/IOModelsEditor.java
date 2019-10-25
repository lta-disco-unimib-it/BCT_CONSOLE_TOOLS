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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.IoExpression;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIMapperFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions.IFindActionEditorSupport;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets.IOModelsEditorComposite;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets.IoModelsFindDialog;

import java.net.URI;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.EditorPart;

public class IOModelsEditor extends EditorPart implements IFindActionEditorSupport {
	private URI uri;
	private IoModel ioModel;
	private List<IoExpression> ioEnterExpression, ioExitExpression;
	private IOModelsEditorComposite enterComposite, exitComposite;
	private boolean dirty;
	private SashForm sash;

	public IOModelsEditor() {
		dirty = false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		monitor.beginTask("Saving " + uri.getFragment() + " IO Model", IProgressMonitor.UNKNOWN);

		updateIoModel();

		MonitoringConfiguration mc = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(uri);
		try {
			URIMapperFactory.getMapper(mc).saveIoModel(ioModel);
		} catch (MapperException e) {
			Logger.getInstance().log(e);
		}

		monitor.done();
		dirty = false;
		firePropertyChange(PROP_DIRTY);
	}

	@Override
	public void doSaveAs() {
		// We do not provide a save as functionality
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.setSite(site);
		super.setInput(input);

		if (input instanceof FileStoreEditorInput) {
			FileStoreEditorInput fsInput = (FileStoreEditorInput) input;
			uri = fsInput.getURI();
			super.setPartName(fsInput.getName());
			loadIOModels();
		} else {
			throw new PartInitException("IOModelsEditor input must be a FileStoreEditorInput instance!");
		}
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		sash = new SashForm(parent, SWT.VERTICAL);
		enterComposite = new IOModelsEditorComposite(this, sash, SWT.BORDER, "Enter IO Invariants");
		exitComposite = new IOModelsEditorComposite(this, sash, SWT.BORDER, "Exit IO Invariants");
		if (ioEnterExpression != null)
			enterComposite.populateTable(ioEnterExpression);
		if (ioExitExpression != null)
			exitComposite.populateTable(ioExitExpression);
	}

	@Override
	public void setFocus() {
		sash.setFocus();
	}

	private void loadIOModels() {
		MonitoringConfiguration mc = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(uri);

		try {
			ioModel = URIMapperFactory.getMapper(mc).getIoModel(uri);
			try {
				ioEnterExpression = ioModel.getExpressionsEnter();
			} catch (LoaderException e) {
				Logger.getInstance().log(e);
			}
			try {
				ioExitExpression = ioModel.getExpressionsExit();
			} catch (LoaderException e) {
				Logger.getInstance().log(e);
			}
		} catch (MapperException e) {
			Logger.getInstance().log(e);
		}
	}

	private void updateIoModel() {
		ioModel.setExpressionsEnter(enterComposite.getIoExpressions());
		ioModel.setExpressionsExit(exitComposite.getIoExpressions());
	}

	public void setDirty() {
		dirty = true;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	public void executeFindAction() {
		IoModelsFindDialog dialog = new IoModelsFindDialog(enterComposite.getShell());

		if (dialog.open() == Dialog.OK) {
			String regex = dialog.getInputText();
			if (regex != null && !regex.isEmpty()) {
				enterComposite.deselectAll();
				exitComposite.deselectAll();
				if (dialog.findEnterInvariants()) {
					enterComposite.find(regex);
				}
				if (dialog.findExitInvariants()) {
					exitComposite.find(regex);
				}
			}
		}
	}
}
