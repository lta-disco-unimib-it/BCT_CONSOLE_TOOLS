package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ComponentsConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.ComponentsConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.ComponentsConfigurationSerializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentManagerException;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentsConfigurationComposite;

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;

public class BctComponentsConfigurationEditor implements IEditorPart {

	
	private IFile sourceFile;
	private ComponentsConfigurationComposite componentsConfigurationComposite;
	private IEditorSite site;
	private IEditorInput editorInput;
	
	private String title = "";
	private String toolTip = "";

	public BctComponentsConfigurationEditor() {
		
	}
	
	
	
	public void dispose() {
		
	}
	public IEditorInput getEditorInput() {
		return editorInput;
	}
	public IEditorSite getEditorSite() {
		return site;
	}
	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		this.site = site;
		this.editorInput = editorInput;
		IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
		sourceFile = fileEditorInput.getFile();
		
		
		
		
	}
	public void addPropertyListener(IPropertyListener listener) {
		System.out.println(listener.toString());
	}
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 1;

		componentsConfigurationComposite = new ComponentsConfigurationComposite(composite, SWT.NONE);

		if ( sourceFile == null){
			return;
		}
		
		
		ComponentsConfiguration monitoringConfiguration;
		try {
			monitoringConfiguration = ComponentsConfigurationDeserializer.deserialize(sourceFile.getLocation().toFile());
			componentsConfigurationComposite.load(monitoringConfiguration);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ComponentManagerException e) {
			MessageDialog.openError(new Shell(), "Error", "Cannot load the configuration "+e.getMessage());
		}
		
	}
	public IWorkbenchPartSite getSite() {
		return site;
	}
	
	public String getTitle() {
		return title;
	}
	public Image getTitleImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(
                ISharedImages.IMG_DEF_VIEW);
	}
	public String getTitleToolTip() {
		return toolTip;
	}
	public void removePropertyListener(IPropertyListener listener) {
		
	}
	
	public void setFocus() {
		
	}
	
	public Object getAdapter(Class adapter) {
		return null;
	}
	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		save(sourceFile);
	}
	
	
	
	
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {
		
		SaveAsDialog dialog= new SaveAsDialog(null);


		dialog.create();

		

		if (dialog.open() == Window.CANCEL) {
			return;
		}
		
		IPath filePath= dialog.getResult();
		if (filePath == null) {
			return;
		}
		
		IWorkspace workspace= ResourcesPlugin.getWorkspace();
		IFile file= workspace.getRoot().getFile(filePath);
		
		save(file);

	}
	private void save(IFile file) {
		ComponentsConfiguration mc = componentsConfigurationComposite.createComponentsConfiguration();
		try {
			ComponentsConfigurationSerializer.serialize(file.getLocation().toFile(), mc);
		} catch (FileNotFoundException e) {
			
		}
	}
	public boolean isDirty() {
		return true;
	}
	public boolean isSaveAsAllowed() {
		return true;
	}
	public boolean isSaveOnCloseNeeded() {
		return true;
	}

}
