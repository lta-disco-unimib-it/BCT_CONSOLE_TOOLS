package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors;


import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

import util.FileIndexAppend;

/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class IndexEditor extends MultiPageEditorPart implements IResourceChangeListener{


	private ScrolledComposite composite;
	private IEditorSite site;
	private IEditorInput editorInput;
	private IFile sourceFile;
	private FileIndexAppend indexContent;
	private IndexTableComposite indexTableComposite;
	/**
	 * Creates a multi-page editor example.
	 */
	public IndexEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}
	/**
	 * Creates page 0 of the multi-page editor,
	 * which contains a text editor.
	 */
	void createPage0() {
		composite = new ScrolledComposite(getContainer(), SWT.DEFAULT);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);
			
		composite.setLayoutData(createFill());
			
		indexTableComposite = new IndexTableComposite(composite, SWT.NONE);
		int index = addPage(composite);
		setPageText(index, "Resources");
		composite.setContent(indexTableComposite);
		composite.setMinSize(indexTableComposite.computeSize(SWT.DEFAULT,SWT.DEFAULT));
	}
	
	private static GridData createFill()
    {
        GridData gd = new GridData();
        gd.horizontalAlignment = 4;
        gd.grabExcessHorizontalSpace = true;
        gd.verticalAlignment = 4;
        gd.grabExcessVerticalSpace = true;
        return gd;
    }
	
	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createPage0();
		
		if ( indexContent != null ){
			for ( Object id : indexContent.getIds() ){
				String file = (String) id;
				try {
					String element = indexContent.getNameFromId(file);
					IFile fileToOpen = sourceFile.getParent().getFile(new Path(file));
					
					long length = fileToOpen.getLocation().toFile().length();
					
					
					indexTableComposite.addElement(element, file, ""+length, fileToOpen);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}

	}
	/**
	 * The <code>MultiPageEditorPart</code> implementation of this 
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
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
		;
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		
	}
	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
		throws PartInitException {
		
		
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
		
		this.site = site;
		this.editorInput = editorInput;
		IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
		sourceFile = fileEditorInput.getFile();
		
		File fileToOpen = sourceFile.getLocation().toFile();
		if ( fileToOpen.exists() ){
			indexContent = new FileIndexAppend(fileToOpen);
		}
		
		
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event){
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
			Display.getDefault().asyncExec(new Runnable(){
				public void run(){
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					
				}            
			});
		}
	}
	
	

}
