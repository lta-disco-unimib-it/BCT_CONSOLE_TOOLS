package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.Trace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIMapperFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions.IFindActionEditorSupport;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.widgets.ProgramPointsTreeComposite;

import java.net.URI;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.EditorPart;

public class IOTracesEditor extends EditorPart implements IFindActionEditorSupport {

	private URI uri;
	private List<? extends ProgramPoint> programPoints;
	private Trace ioTrace;
	private ProgramPointsTreeComposite tree;
	private boolean dirty;
	private MonitoringConfiguration mc;
	
	public IOTracesEditor() {
		super();
		dirty = false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		monitor.beginTask("Saving " + uri.getFragment() + " IO Raw Trace", IProgressMonitor.UNKNOWN);

		updateIoTrace();
		
		MonitoringConfiguration mc = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(uri);
		try {
			URIMapperFactory.getMapper(mc).saveIoTrace(ioTrace);
		} catch (MapperException e) {
			Logger.getInstance().log(e);
		}

		monitor.done();
		dirty = false;
		firePropertyChange(PROP_DIRTY);
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
			super.setPartName(fsInput.getName());
			loadIOTraces();
		} else {
			throw new PartInitException(getClass().getSimpleName() + " input must be a FileStoreEditorInput instance!");
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
		tree = new ProgramPointsTreeComposite(parent, SWT.NONE, this);
		tree.populateTree(programPoints);
	}

	@Override
	public void setFocus() {
		tree.setFocus();
	}

	private void loadIOTraces() {
		mc = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(uri);
		
		try {
			ioTrace = URIMapperFactory.getMapper(mc).getIoTrace(uri);
			if (ioTrace instanceof IoRawTrace) {
				IoRawTrace rawTrace = (IoRawTrace) ioTrace;
				programPoints = rawTrace.getProgramPoints();
			} else if (ioTrace instanceof IoNormalizedTrace) {
				IoNormalizedTrace normalizedTrace = (IoNormalizedTrace) ioTrace;
				programPoints = normalizedTrace.getProgramPoints();	 
			}
		} catch (MapperException e) {
			Logger.getInstance().log(e);
		} catch (LoaderException e) {
			Logger.getInstance().log(e);
		}
	}

	private void updateIoTrace() {
		tree.updateProgramPoints();
	}
	
	public void setDirty() {
		dirty = true;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	public void executeFindAction() {
		InputDialog inputDialog = new InputDialog(tree.getParent().getShell(), "Find",
				"Search items by regular expression:", "Regular Expression", null);
		if (inputDialog.open() == Dialog.OK) {
			String regex = inputDialog.getValue();
			tree.find(regex);
		}
	}

	public MonitoringConfiguration getMonitoringConfiguration() {
		return this.mc;
	}

	
}
