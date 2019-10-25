package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
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

public class IOModelsIndexEditor extends EditorPart implements IFindActionEditorSupport {

	private URI uri;
	private List<IoModel> ioModels;
	private VirtualTable<IoModel> table;
	private MonitoringConfiguration mc;

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
			super.setPartName("IO Models Index");
			loadIOModels();
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
		table = new VirtualTable<IoModel>(parent, new String[] {"Method signature"});
		table.setMonitoringConfiguration(mc);
		table.setContent(ioModels);
	}

	@Override
	public void setFocus() {
		table.setFocus();
	}

	//------------- Methods not in IEditorPart interface -------------------
	
	private void loadIOModels() {
		mc = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(uri);
		
		try {
			ioModels = URIMapperFactory.getMapper(mc).getIoModels(uri);
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
