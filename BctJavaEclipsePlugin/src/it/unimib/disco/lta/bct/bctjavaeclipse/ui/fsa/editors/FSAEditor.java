package it.unimib.disco.lta.bct.bctjavaeclipse.ui.fsa.editors;

import gui.environment.EnvironmentFrame;
import gui.environment.FrameFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.components.ComponentsConfigurationComposite;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
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

import automata.fsa.FiniteStateAutomaton;

public class FSAEditor implements IEditorPart {

	
	private IFile sourceFile;
	private ComponentsConfigurationComposite componentsConfigurationComposite;
	private IEditorSite site;
	private IEditorInput editorInput;
	
	private String title = "";
	private String toolTip = "";

	public FSAEditor() {
		
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

		

		if ( sourceFile == null){
			return;
		}
		
		
		
		try {
			EnvironmentFrame f = FrameFactory.createFrame(FiniteStateAutomaton.readSerializedFSA(sourceFile.getLocation().toFile().getAbsolutePath()));
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			
		} catch (ClassNotFoundException e) {
			MessageDialog.openError(new Shell(), "Error", "Cannot open file "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
	}
	
	
	
	
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {
		
		

	}
	private void save(IFile file) {
		
	}
	public boolean isDirty() {
		return false;
	}
	public boolean isSaveAsAllowed() {
		return false;
	}
	public boolean isSaveOnCloseNeeded() {
		return false;
	}

}
