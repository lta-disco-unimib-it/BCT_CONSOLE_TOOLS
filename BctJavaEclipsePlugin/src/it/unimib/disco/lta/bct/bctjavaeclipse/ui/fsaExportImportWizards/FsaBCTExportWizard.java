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

import fsa.FSA;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.FSAJABCExporter;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import tools.fsa2xml.codec.api.FSACodec;
import tools.fsa2xml.codec.factory.FSA2FileFactory;
import automata.fsa.FiniteStateAutomaton;


/**
 * eclipse wizard export for .fsa file
 * 
 * 
 * @author Terragni Valerio
 *
 */



public class FsaBCTExportWizard extends Wizard implements IExportWizard {

	FsaBCTExportWizardPage exportPage; //the wizard page

	public FsaBCTExportWizard() {
		super();
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("Fsa Export Wizard"); 
		setNeedsProgressMonitor(true);
		exportPage = new FsaBCTExportWizardPage("Export File",selection); 
	}


	public void addPages() {
		super.addPages(); 
		addPage(exportPage);        
	}

	public boolean canFinish(){
		return exportPage.isPageComplete(); 
	}


	public boolean performFinish() {

		File destinationFolder = exportPage.getDestinationFolder(); 
		//is the destination folder, where the file will be exported

		File[] checkedElements = exportPage.getFileToExport();
		// contains all checked element of CheckBoxTreeView


		if (exportPage.getSerSelection()){ //if the ser checkbox is checked


			for(int i = 0;i<checkedElements.length;i++){

				if(!( checkedElements[i]).isDirectory()){
					export(checkedElements[i],destinationFolder,".ser");
					//if the selected file isn't a directory, call export method

				}
			}

		}

		// JFlap export
		if (exportPage.getJFlapXmlSelection()){


			for(int i = 0;i<checkedElements.length;i++){

				if(!( checkedElements[i]).isDirectory()){

					export(checkedElements[i],destinationFolder,".xml");
				}
			}

		}
		// JEti export
		if (exportPage.getJEtiXmlSelection()){


			for(int i = 0;i<checkedElements.length;i++){

				if(!( checkedElements[i]).isDirectory()){

					export(checkedElements[i],destinationFolder,".jabc");
				}
			}

		}


		Shell parent = this.getShell();
		MessageBox messageBox = new MessageBox(parent, SWT.ICON_INFORMATION | SWT.OK);
		messageBox.setText("Information");
		messageBox.setMessage("files created ");  
		messageBox.open(); 



		return true; // wizard is done 

	}
	/**
	 * export a fsa file into a Ser,Jflap,JEti file
	 * 
	 * @param source     
	 * @param destFolder
	 * @param extension
	 */
	public void export( File source, File destFolder, String extension ){
		FSACodec fsaBctCodec = FSA2FileFactory.getFSABctXml();
		FiniteStateAutomaton fsa;
		boolean cancel = false; 
		try {

			fsa = fsaBctCodec.loadFSA(source);// laod FiniteStateAutomaton from fsa file
			String nameFile = ( source).getName().replace(".fsa", extension); 
			// get File name and change extension

			String path = destFolder.getAbsolutePath() + File.separator  +nameFile;
			//path export file

			if (( new File (path)).exists()){ // if the file already exist, open a messagbox for make user to choose
				Shell parent = this.getShell();
				MessageBox messageBoxAsk = new MessageBox(parent , SWT.ICON_WARNING | SWT.OK| SWT.CANCEL);

				messageBoxAsk.setText("Warning");
				messageBoxAsk.setMessage("the file " + path + " already exist, Do you want to replace the existing file? ");
				int answer = messageBoxAsk.open();

				switch (answer) {
				case SWT.CANCEL:
				}

				cancel = true;

			}

			if(cancel == false){ // the file no already exist, or the user want replace it
				if(extension.equals(".ser")){
					FSACodec serCodec = FSA2FileFactory.getFSASer();
					serCodec.saveFSA(fsa, path); // make a ser file
				}
				if(extension.equals(".xml")){
					FSACodec xmlCodec = FSA2FileFactory.getFSAXml();
					xmlCodec.saveFSA(fsa, path); // make a Jabc file
				}
				if(extension.equals(".jabc")){

					// Create a resource set.
					ResourceSet resourceSet = new ResourceSetImpl();

					// Register the default resource factory -- only needed for stand-alone!
					resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
							Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());




					// Get the URI of the model file.
					URI fileURI = URI.createFileURI(source.getAbsolutePath());

					// Demand load the resource for this file.
					Resource resource = resourceSet.getResource(fileURI, true);
					for ( EObject o : resource.getContents()){
						if ( o instanceof fsa.FSA ){
							fsa.FSA FSA = (FSA) o;
							FSAJABCExporter.exportToJABC(FSA,new File(path));
						}
					}

				}

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




