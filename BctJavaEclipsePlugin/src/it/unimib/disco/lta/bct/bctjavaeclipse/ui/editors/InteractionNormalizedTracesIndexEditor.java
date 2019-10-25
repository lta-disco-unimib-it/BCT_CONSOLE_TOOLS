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

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIMapperFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions.IFindActionEditorSupport;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets.VirtualTable;

import java.net.URI;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.EditorPart;

public class InteractionNormalizedTracesIndexEditor extends EditorPart implements IFindActionEditorSupport {

	private URI uri;
	private MonitoringConfiguration mc;
	private List<InteractionNormalizedTrace> traces;
	private VirtualTable<InteractionNormalizedTrace> table;

	@Override
	public void doSave(IProgressMonitor monitor) {
		//We do not provide a save functionality
	}

	@Override
	public void doSaveAs() {
		//We do not provide a save as functionality
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.setSite(site);
		super.setInput(input);

		if (input instanceof FileStoreEditorInput) {
			FileStoreEditorInput fsInput = (FileStoreEditorInput) input;
			uri = fsInput.getURI();
			super.setPartName("Normalized Interaction Traces Index");
			loadInteractionTraces();
		} else {
			throw new PartInitException(getClass().getSimpleName() + " input must be a FileStoreEditorInput instance!");
		}
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
		table = new VirtualTable<InteractionNormalizedTrace>(parent, new String[] {"Method signature"});
		table.setMonitoringConfiguration(mc);
		table.setContent(traces);
	}

	@Override
	public void setFocus() {
		table.setFocus();
	}

	//------------- Methods not in IEditorPart interface -------------------
	
	private void loadInteractionTraces() {
		mc = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(uri);
		
		try {
			traces = URIMapperFactory.getMapper(mc).getInteractionNormalizedTraces(uri);
		} catch (MapperException e) {
			Logger.getInstance().log(e);
		}
	}

	public void executeFindAction() {
		InputDialog inputDialog = new InputDialog(table.getParent().getShell(), "Find",
				"Search items by regular expression:", "Regular Expression", null);
		if (inputDialog.open() == Dialog.OK) {
			String regex = inputDialog.getValue();
			table.find(regex);
		}	
	}
}
