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
import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import tools.fsa2xml.codec.api.FSACodec;
import tools.fsa2xml.codec.factory.FSA2FileFactory;
import automata.fsa.FiniteStateAutomaton;
/**
 * 
 * import Ser,Jflap into a fsa file
 *
 *@author Terragni Valerio
 */


public class FsaBCTImportWizard extends Wizard implements IImportWizard {

	public FsaBCTImportWizard() {
		super();
	}


	FsaBCTImportWizardPage importPage; 


	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("Fsa Import Wizard"); 
		setNeedsProgressMonitor(true);
		importPage = new FsaBCTImportWizardPage("Import File",selection); // create import page
	}


	public void addPages() {
		super.addPages(); 
		addPage(importPage);        
	}

	public boolean canFinish(){
		return importPage.isPageComplete();
	}


	public boolean performFinish() {

		File destinationFolder = importPage.getDestinationFolder();// path for destination directory


		File[] checkedElements = importPage.getFileToExport(); // checked elements

		for(int i = 0;i<checkedElements.length;i++){   

			if(!( checkedElements[i]).isDirectory()){ // for each file elements call import method

				importFile(checkedElements[i],destinationFolder);

			}
		}


		Shell parent = this.getShell();
		MessageBox messageBox = new MessageBox(parent, SWT.ICON_INFORMATION | SWT.OK);
		messageBox.setText("Information");
		messageBox.setMessage("files created ");  
		messageBox.open(); 



		return true;

	}
/**
 * method to make a fsa file from source file, into a destinationfolder
 * 
 * @param source
 * @param destFolder
 */
	public void importFile( File source, File destFolder){ // method import file 

		FiniteStateAutomaton fsa = null;
		boolean cancel = false;
		try {


			String nameFile = (source).getName(); // name file

			if (nameFile.endsWith(".ser")){ // the extension is ser

				FSACodec fsaSerCodec = FSA2FileFactory.getFSASer();
				fsa = fsaSerCodec.loadFSA(source);
				nameFile = nameFile.replace(".ser", ".fsa");//change extension

			}else if (nameFile.endsWith(".xml")){ // the extension is jflap

				FSACodec fsaXmlCodec = FSA2FileFactory.getFSAXml();
				fsa = fsaXmlCodec.loadFSA(source.getAbsolutePath());
				nameFile = nameFile.replace(".xml", ".fsa"); //change extension
			}

			//make path
			String path = destFolder.getAbsolutePath() + File.separator  +nameFile;

			if (( new File (path)).exists()){  //ask if the file already exist
				Shell parent = this.getShell();
				MessageBox messageBoxAsk = new MessageBox(parent , SWT.ICON_WARNING | SWT.OK| SWT.CANCEL);

				messageBoxAsk.setText("Warning");
				messageBoxAsk.setMessage("the file " + path + " already exist, Do you want to replace the existing file? ");
				int answer = messageBoxAsk.open();  //open messagebox

				switch (answer) {
				case SWT.CANCEL:
				}

				cancel = true;

			}

			if(cancel == false){   // create a fsa file

				FSACodec serCodec = FSA2FileFactory.getFSABctXml();
				serCodec.saveFSA(fsa, path);

			}


		} catch (IOException e) {
			MessageDialog.openError(getShell(), "Error", "Error while exporting FSA: "+source);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			MessageDialog.openError(getShell(), "Error", "Error while exporting FSA: "+source);
			e.printStackTrace();
		} // load FiniteStateAutomaton


	}

}
