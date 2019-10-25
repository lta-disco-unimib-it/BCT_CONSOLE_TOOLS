/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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












	 