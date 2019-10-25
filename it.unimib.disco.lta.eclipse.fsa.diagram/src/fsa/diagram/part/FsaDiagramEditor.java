package fsa.diagram.part;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.util.DiagramFileCreator;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;

import fsa.diagram.edit.parts.FSAEditPart;
import fsa.diagram.navigator.FsaNavigatorItem;
import fsa.impl.FsaFactoryImpl;

/**
 * @generated
 */
public class FsaDiagramEditor extends DiagramDocumentEditor implements
IGotoMarker {
	public IEditorInput inPut;
	public EObject selectedModelElement;

	/**
	 * @generated
	 */
	public static final String ID = "fsa.diagram.part.FsaDiagramEditorID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final String CONTEXT_ID = "fsa.diagram.ui.diagramContext"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public FsaDiagramEditor() {
		super(true);
	}

	/**
	 * @generated
	 */
	protected String getContextID() {
		return CONTEXT_ID;
	}

	/**
	 * @generated
	 */
	protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot) {
		PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
		new FsaPaletteFactory().fillPalette(root);
		return root;
	}

	/**
	 * @generated
	 */
	protected PreferencesHint getPreferencesHint() {
		return FsaDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
	}

	/**
	 * @generated
	 */
	public String getContributorId() {
		return FsaDiagramEditorPlugin.ID;
	}

	/**
	 * @generated
	 */
	public Object getAdapter(Class type) {
		if (type == IShowInTargetList.class) {
			return new IShowInTargetList() {
				public String[] getShowInTargetIds() {
					return new String[] { ProjectExplorer.VIEW_ID };
				}
			};
		}
		return super.getAdapter(type);
	}

	/**
	 * @generated
	 */
	protected IDocumentProvider getDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput
				|| input instanceof URIEditorInput) {
			return FsaDiagramEditorPlugin.getInstance().getDocumentProvider();
		}
		return super.getDocumentProvider(input);
	}

	/**
	 * @generated
	 */
	public TransactionalEditingDomain getEditingDomain() {
		IDocument document = getEditorInput() != null ? getDocumentProvider()
				.getDocument(getEditorInput()) : null;
				if (document instanceof IDiagramDocument) {
					return ((IDiagramDocument) document).getEditingDomain();
				}
				return super.getEditingDomain();
	}

	/**
	 * @generated
	 */
	protected void setDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput
				|| input instanceof URIEditorInput) {
			setDocumentProvider(FsaDiagramEditorPlugin.getInstance()
					.getDocumentProvider());
		} else {
			super.setDocumentProvider(input);
		}
	}

	/**
	 * @generated
	 */
	public void gotoMarker(IMarker marker) {
		MarkerNavigationService.getInstance().gotoMarker(this, marker);
	}

	/**
	 * @generated
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * @generated
	 */
	public void doSaveAs() {
		performSaveAs(new NullProgressMonitor());
	}

	/**
	 * @generated
	 */
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		IEditorInput input = getEditorInput();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		IFile original = input instanceof IFileEditorInput ? ((IFileEditorInput) input)
				.getFile()
				: null;
				if (original != null) {
					dialog.setOriginalFile(original);
				}
				dialog.create();
				IDocumentProvider provider = getDocumentProvider();
				if (provider == null) {
					// editor has been programmatically closed while the dialog was open
					return;
				}
				if (provider.isDeleted(input) && original != null) {
					String message = NLS.bind(
							Messages.FsaDiagramEditor_SavingDeletedFile, original
							.getName());
					dialog.setErrorMessage(null);
					dialog.setMessage(message, IMessageProvider.WARNING);
				}
				if (dialog.open() == Window.CANCEL) {
					if (progressMonitor != null) {
						progressMonitor.setCanceled(true);
					}
					return;
				}
				IPath filePath = dialog.getResult();
				if (filePath == null) {
					if (progressMonitor != null) {
						progressMonitor.setCanceled(true);
					}
					return;
				}
				IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
				IFile file = workspaceRoot.getFile(filePath);
				final IEditorInput newInput = new FileEditorInput(file);
				// Check if the editor is already open
				IEditorMatchingStrategy matchingStrategy = getEditorDescriptor()
				.getEditorMatchingStrategy();
				IEditorReference[] editorRefs = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
				for (int i = 0; i < editorRefs.length; i++) {
					if (matchingStrategy.matches(editorRefs[i], newInput)) {
						MessageDialog.openWarning(shell,
								Messages.FsaDiagramEditor_SaveAsErrorTitle,
								Messages.FsaDiagramEditor_SaveAsErrorMessage);
						return;
					}
				}
				boolean success = false;
				try {
					provider.aboutToChange(newInput);
					getDocumentProvider(newInput).saveDocument(progressMonitor,
							newInput,
							getDocumentProvider().getDocument(getEditorInput()), true);
					success = true;
				} catch (CoreException x) {
					IStatus status = x.getStatus();
					if (status == null || status.getSeverity() != IStatus.CANCEL) {
						ErrorDialog.openError(shell,
								Messages.FsaDiagramEditor_SaveErrorTitle,
								Messages.FsaDiagramEditor_SaveErrorMessage, x
								.getStatus());
					}
				} finally {
					provider.changed(newInput);
					if (success) {
						setInput(newInput);
					}
				}
				if (progressMonitor != null) {
					progressMonitor.setCanceled(!success);
				}
	}

	/**
	 * @generated
	 */
	public ShowInContext getShowInContext() {
		return new ShowInContext(getEditorInput(), getNavigatorSelection());
	}

	/**
	 * @generated
	 */
	private ISelection getNavigatorSelection() {
		IDiagramDocument document = getDiagramDocument();
		if (document == null) {
			return StructuredSelection.EMPTY;
		}
		Diagram diagram = document.getDiagram();
		IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
		if (file != null) {
			FsaNavigatorItem item = new FsaNavigatorItem(diagram, file, false);
			return new StructuredSelection(item);
		}
		return StructuredSelection.EMPTY;
	}


	/** 
	 * setInput must be modify because we wont that a .fsa file can be open with a fsa editor,
	 * so setinput method must be overriden 
	 * 
	 */
	public void setInput(IEditorInput input) { 

		IFile myFile = (IFile)input.getAdapter(IFile.class); // get current file



		if (myFile.getFileExtension().equals("fsa")) { // if is a fsa file


			URI domainModelURI = URI.createPlatformResourceURI(myFile.getFullPath()
					.toString(), true);  //create uri

			TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE
			.createEditingDomain();   // create editing domain


			ResourceSet resourceSet = editingDomain.getResourceSet();

			EObject diagramRoot = null;
			try {
				Resource resource = resourceSet.getResource(domainModelURI, true);

				diagramRoot = (EObject) resource.getContents().get(0); // get diagram root

			} catch (WrappedException ex) {
				FsaDiagramEditorPlugin.getInstance().logError(
						"Unable to load resource: " + domainModelURI, ex); //$NON-NLS-1$
			}


			List affectedFiles = new LinkedList(); 	

			IPath diagramPath = myFile.getParent().getFullPath().append(myFile.getName()+"_diagram"); //path fsa_diagram file


			IFile di = ResourcesPlugin.getWorkspace().getRoot().getFile(diagramPath);

			IFile diagramFile;
			if (di.isAccessible()){ // id the .fsa_diagram exist, open it 

				diagramFile = di;
				inPut = new FileEditorInput(diagramFile);
				super.setInput(inPut);
			} else { // else create it
				diagramFile = createNewFile(di); // create current fsa_diagram file
				FsaDiagramEditorUtil.setCharset(diagramFile); 
				affectedFiles.add(diagramFile);
				URI diagramModelURI = URI.createPlatformResourceURI(diagramFile
						.getFullPath().toString(), true);
				resourceSet = editingDomain.getResourceSet();
				final Resource diagramResource = resourceSet

				.createResource(diagramModelURI);

				setModelElement(diagramRoot);

				AbstractTransactionalCommand command = new AbstractTransactionalCommand(
						editingDomain,
						Messages.FsaNewDiagramFileWizard_InitDiagramCommand,
						affectedFiles) {

					protected CommandResult doExecuteWithResult(
							IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {

						int diagramVID = FsaVisualIDRegistry
						.getDiagramVisualID(selectedModelElement);

						if (diagramVID != FSAEditPart.VISUAL_ID) {
							return CommandResult
							.newErrorCommandResult(Messages.FsaNewDiagramFileWizard_IncorrectRootError);
						}
						Diagram diagram = ViewService.createDiagram(
								selectedModelElement,
								FSAEditPart.MODEL_ID,
								FsaDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
						diagramResource.getContents().add(diagram);
						return CommandResult.newOKCommandResult();
					}
				};
				try {
					OperationHistoryFactory.getOperationHistory().execute(command,
							new NullProgressMonitor(), null);
					diagramResource.save(FsaDiagramEditorUtil.getSaveOptions());


					inPut = new FileEditorInput(diagramFile);
					super.setInput(inPut);

				} catch (ExecutionException e) {
					FsaDiagramEditorPlugin.getInstance().logError(
							"Unable to create model and diagram", e); //$NON-NLS-1$
				} catch (IOException ex) {
					FsaDiagramEditorPlugin.getInstance().logError(
							"Save operation failed for: " + diagramModelURI, ex); //$NON-NLS-1$
				}


			}


		}else{


			super.setInput(input);


		}

	}	



	private void setModelElement(EObject modelElement) {
		selectedModelElement = modelElement;


	}

	public IFile createNewFile(final IFile newFileHandle) {


		// create the new file and cache it if successful
		System.out.println("Create new file");


		final InputStream initialContents = null;


		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				CreateFileOperation op = new CreateFileOperation(newFileHandle,
						null, initialContents,
						IDEWorkbenchMessages.WizardNewFileCreationPage_title);
				try {
					PlatformUI.getWorkbench().getOperationSupport()
					.getOperationHistory().execute(
							op,
							monitor,
							WorkspaceUndoUtil
							.getUIInfoAdapter(null));
				} catch (final ExecutionException e) {
					//FIXME: error msg
				}
			}
		};

		NullProgressMonitor runner = new NullProgressMonitor();
		ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(IDEWorkbenchPlugin.getDefault().getWorkbench().getDisplay().getActiveShell());
		try {
			progressMonitorDialog.run(true, true, op);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		return newFileHandle;
	}


}
