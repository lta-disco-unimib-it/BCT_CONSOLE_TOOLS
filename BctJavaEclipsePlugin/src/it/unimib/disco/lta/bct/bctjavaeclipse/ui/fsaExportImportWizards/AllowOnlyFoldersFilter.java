package it.unimib.disco.lta.bct.bctjavaeclipse.ui.fsaExportImportWizards;




import java.io.File;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
/**
 * this class is the filter for treeview, make visible only workspace folders 
 * and hide ".metadata" folder.
 * 
 * @author Terragni Valerio
 *
 */


public class AllowOnlyFoldersFilter extends ViewerFilter {
	public boolean select(Viewer viewer, Object parent, Object element) {


		if(((File) element).getName().equals(".metadata")) // the folder metadata must be hidden
			return false;


		return ((File) element).isDirectory();  //return true only is a directory
	}
}












	 