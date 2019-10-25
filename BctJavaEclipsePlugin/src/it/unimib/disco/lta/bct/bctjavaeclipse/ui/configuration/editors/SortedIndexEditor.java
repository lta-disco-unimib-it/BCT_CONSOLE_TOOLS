package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration.editors;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import util.FileIndexAppend;

public class SortedIndexEditor extends EditorPart {
	private FileIndexAppend indexContent;
	private IFile sourceFile;
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		
		super.setSite(site);
		super.setInput(input);
		
		IFileEditorInput fileEditorInput = (IFileEditorInput) input;
		sourceFile = fileEditorInput.getFile();
		
		File fileToOpen = sourceFile.getLocation().toFile();
		if (fileToOpen.exists()){
			indexContent = new FileIndexAppend(fileToOpen);
		}
		
		super.setPartName(sourceFile.getName());
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void createPartControl(Composite parent) {
		String[] headers = new String[] {"Element", "Resource", "Size"};
		SortedIndexTable table = new SortedIndexTable(parent, SWT.SINGLE | SWT.FULL_SELECTION, headers);
		
		if ( indexContent != null ){
			for ( Object id : indexContent.getIds() ){
				String file = (String) id;
				try {
					String element = indexContent.getNameFromId(file);
					IFile fileToOpen = sourceFile.getParent().getFile(new Path(file));
					long length = fileToOpen.getLocation().toFile().length();
					table.addElement(element, file, ""+length, fileToOpen);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}
}
