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

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
/**
 * this class in the label provider of treeview and checkboxtreeview
 * provide the label and text of node
 *
 *@author Terragni Valerio
 */
public class FileTreeLabelProvider extends LabelProvider
{
	public String getText(Object element) 
	{


		String text = ((File) element).getName();
		// name is blank means element is the drive root, get the path
		if (text.length() == 0) {
			text = ((File) element).getPath();
		}



		return text;



	}

	/**
	 * get image node
	 *
	 */

	public Image getImage(Object element) { 

		if((Boolean) ((File) element).isDirectory()) 
			//case is directory
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
		else
			// case is file
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE); 

	}

}
