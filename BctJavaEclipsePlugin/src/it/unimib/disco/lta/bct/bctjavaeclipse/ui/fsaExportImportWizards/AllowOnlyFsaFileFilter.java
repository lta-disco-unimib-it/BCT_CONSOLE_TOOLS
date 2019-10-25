package it.unimib.disco.lta.bct.bctjavaeclipse.ui.fsaExportImportWizards;

import java.io.File;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
/**
 * this class is the filter for CheckBoxTreeView, make visible only workspace folders, and FSA file
 * and hide ".metadata" folder.
 * 
 *@author Terragni Valerio
 */
public class AllowOnlyFsaFileFilter extends ViewerFilter {

	public boolean select(Viewer viewer, Object parent, Object element) {


		if (((File) element).isDirectory()){
			if(((File) element).getName().equals(".metadata")) // the folder metadata must be hidden
				return false;
			else 
				return true;

		}
		if(((File) element).getName().endsWith(".fsa")) // only fsa file must be show in tree
			return true;



		return false;


	}



}
