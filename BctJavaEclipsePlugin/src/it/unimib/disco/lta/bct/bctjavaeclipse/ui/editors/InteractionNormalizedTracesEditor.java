package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCall;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.MethodCallSequence;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIMapperFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.actions.IFindActionEditorSupport;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.EditorPart;

public class InteractionNormalizedTracesEditor extends EditorPart implements IFindActionEditorSupport {

	private URI uri;
	private InteractionNormalizedTrace trace;
	private List<MethodCallSequence> callSequences;
	private Tree tree;

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
			super.setPartName(fsInput.getName());
			loadMethodCallSequence();
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
		//We do not provide a save as functionality
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		tree = new Tree(parent, SWT.VIRTUAL | SWT.MULTI | SWT.FULL_SELECTION);
		tree.setLinesVisible(true);
		parent.setLayout(new FillLayout());
		
		tree.addListener(SWT.SetData, new Listener() {
			public void handleEvent(Event event) {
				TreeItem item = (TreeItem) event.item;
				TreeItem parentItem = item.getParentItem();
				MethodCallSequence sequence = null;
				int index = event.index;
				
				if(parentItem == null) {
					// root-level item
					item.setText("Call sequence number " + (index + 1));
					sequence = callSequences.get(index);
					
					List<MethodCall> methodCalls = new ArrayList<MethodCall>();
					for (Iterator<MethodCall> iterator = sequence.iterator(); iterator.hasNext();) {
						methodCalls.add(iterator.next());
					}
					item.setData(methodCalls.toArray(new MethodCall[0]));
					item.setItemCount(methodCalls.size());
				} else {
					MethodCall[] methodCalls = (MethodCall[]) parentItem.getData();
					item.setText(methodCalls[index].getMethod().getSignature());
				}
			}
		});
		
		tree.setItemCount(callSequences.size());
		tree.setData(callSequences);
	}

	@Override
	public void setFocus() {
		tree.setFocus();
	}

	private void loadMethodCallSequence() {
		MonitoringConfiguration mc = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(uri);

		try {
			trace = URIMapperFactory.getMapper(mc).getInteractionNormalizedTrace(uri);
			callSequences = trace.getCallSequences();
		} catch (MapperException e) {
			Logger.getInstance().log(e);
		} catch (LoaderException e) {
			Logger.getInstance().log(e);
		}
	}

	public void executeFindAction() {
		InputDialog inputDialog = new InputDialog(tree.getParent().getShell(), "Find",
				"Search items by regular expression:", "Regular Expression", null);
		if (inputDialog.open() == Dialog.OK) {
			String regex = inputDialog.getValue();
			List<TreeItem> selection = new ArrayList<TreeItem>();
			
			for (TreeItem item : tree.getItems()) { // For each root item
				for (TreeItem child : item.getItems()) { // For each child item
					if (child.getText().matches(regex)) {
						selection.add(child);
						item.setExpanded(true);
					}
				}
			}
			tree.setSelection(selection.toArray(new TreeItem[0]));
		}		 
	}
}
