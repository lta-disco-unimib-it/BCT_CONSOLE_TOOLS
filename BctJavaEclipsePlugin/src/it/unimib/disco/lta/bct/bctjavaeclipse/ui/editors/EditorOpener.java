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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors;

import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.FSA_MODELS_EDITOR_ID;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.FSA_MODELS_EDITOR_INDEX_ID;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.INTERACTION_MODELS_PATH;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.INTERACTION_NORMALIZED_TRACES_EDITOR_ID;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.INTERACTION_NORMALIZED_TRACES_EDITOR_INDEX_ID;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.INTERACTION_NORMALIZED_TRACES_PATH;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.INTERACTION_RAW_TRACES_EDITOR_ID;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.INTERACTION_RAW_TRACES_EDITOR_INDEX_ID;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.INTERACTION_RAW_TRACES_PATH;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.IO_MODELS_EDITOR_ID;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.IO_MODELS_EDITOR_INDEX_ID;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.IO_MODELS_PATH;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.IO_NORMALIZED_TRACES_PATH;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.IO_RAW_TRACES_PATH;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.IO_TRACES_EDITOR_ID;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.IO_TRACES_EDITOR_INDEX_ID;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.RESOURCE_IDENTIFIER;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.URI_PATH_SEPARATOR;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.FileStorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.StorageConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.URIUtil;

import java.io.File;
import java.net.URI;
import java.util.Hashtable;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import util.FileIndex;
import util.FileIndex.FileIndexException;

/**
 * This class encapsulates editors opening. All classes need to open editors should use this class.
 */
public class EditorOpener {
	private enum EditorInput {
		IO_MODELS, FSA_MODELS, IO_TRACES, INTERACTION_NORMALIZED_TRACES, INTERACTION_RAW_TRACES
	}

	/**
	 * Using URI, finds appropriated editor and opens it.
	 * 
	 * @param display
	 *            SWT widget used to execute asynchronous runnable.
	 * @param uri
	 *            URI referes resource to open with appropriated editor.
	 */
	public static void openEditor(Display display, final URI uri) {
		display.asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				String editorID = getEditorID(uri);
				try {
					
					Logger.getInstance().logInfo("Opening editor (ID: " + editorID + ") with input uri: " + uri, null);
					
					if ( editorID == null ){
						File fileToOpen = org.eclipse.core.runtime.URIUtil.toFile(uri);
						IFile iFileToOpen = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(fileToOpen.getAbsolutePath()));
						IDE.openEditor(page, iFileToOpen, true);
					} else {
						if (!editorID.equals(FSA_MODELS_EDITOR_ID))
							IDE.openEditor(page, uri, editorID, true);
						else
							IDE.openEditor(page, getFsaFile(uri), editorID, true);
					}
				} catch (PartInitException e) {
					Logger.getInstance().log(e);
				}
			}
		});
	}

	/**
	 * Opens Monitoring Configuration file editor. Monitoring Configuration file is a file with .bctmc extension saved
	 * in the workspace on local file system.
	 * 
	 * @param display
	 *            SWT widget used to execute asynchronous runnable
	 * @param monitoringConfiguration
	 *            file to open
	 */
	public static void openMonitoringConfigurationEditor(Display display, final IFile monitoringConfiguration) {
		display.asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, monitoringConfiguration);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Return the ID of the Editor that is able to manage the URI
	 * If none is found returns null
	 * 
	 * @param uri
	 * @return
	 */
	private static String getEditorID(URI uri) {
		
		if ( ! URIUtil.isBCT( uri ) ) {
			return null;
		}
		
		String path = uri.getPath();
		EditorInput editorInputType = getEditorInputType(path);
		
		String query = uri.getQuery();
		Hashtable<String, String> queryStringMap = URIUtil.parseQueryString(query);
		
		String id=queryStringMap.get(RESOURCE_IDENTIFIER);
		if (id != null && id.equals("*"))
			return getResourcesIndexEditorID(editorInputType);
		else
			return getEditorID(editorInputType);
	}
	
	private static String getEditorID(EditorInput editorInputType) {
		switch(editorInputType) {
		case IO_MODELS:
			return IO_MODELS_EDITOR_ID;
		case FSA_MODELS:
			return FSA_MODELS_EDITOR_ID;
		case IO_TRACES:
			return IO_TRACES_EDITOR_ID;
		case INTERACTION_NORMALIZED_TRACES:
			return INTERACTION_NORMALIZED_TRACES_EDITOR_ID;
		case INTERACTION_RAW_TRACES:
			return INTERACTION_RAW_TRACES_EDITOR_ID;
		default:
			return null;
		}
	}

	private static String getResourcesIndexEditorID(EditorInput editorInputType) {
		switch(editorInputType) {
		case IO_MODELS:
			return IO_MODELS_EDITOR_INDEX_ID;
		case FSA_MODELS:
			return FSA_MODELS_EDITOR_INDEX_ID;
		case IO_TRACES:
			return IO_TRACES_EDITOR_INDEX_ID;
		case INTERACTION_NORMALIZED_TRACES:
			return INTERACTION_NORMALIZED_TRACES_EDITOR_INDEX_ID;
		case INTERACTION_RAW_TRACES:
			return INTERACTION_RAW_TRACES_EDITOR_INDEX_ID;
		default:
			return null;
		}
	}

	private static EditorInput getEditorInputType(String path) {
		if (path.equals(IO_MODELS_PATH))
			return EditorInput.IO_MODELS;
		else if (path.equals(INTERACTION_MODELS_PATH))
			return EditorInput.FSA_MODELS;
		else if (path.equals(IO_RAW_TRACES_PATH) || path.equals(IO_NORMALIZED_TRACES_PATH))
			return EditorInput.IO_TRACES;
		else if (path.equals(INTERACTION_NORMALIZED_TRACES_PATH))
			return EditorInput.INTERACTION_NORMALIZED_TRACES;
		else if(path.equals(INTERACTION_RAW_TRACES_PATH))
			return EditorInput.INTERACTION_RAW_TRACES;
		return null;
	}

	/**
	 * Retrieves the file where is stored the FSA model. This method is necessary to open FSA model editor that receives
	 * FSA file instead URI as input.
	 * 
	 * @param uri
	 *            The URI to open with FSA models editor
	 * @return the file where FSA model has been saved.
	 */
	private static IFile getFsaFile(URI uri) {
		MonitoringConfiguration mc = MonitoringConfigurationRegistry.getInstance().getMonitoringConfiguration(uri);
		StorageConfiguration sc = mc.getStorageConfiguration();
		if (sc instanceof FileStorageConfiguration) {
			FileStorageConfiguration fsc = (FileStorageConfiguration) sc;
			String dataDir = fsc.getDataDirPath();
			URI fsaIndexFileURI = URIUtil.buildURI(dataDir + uri.getPath() + URI_PATH_SEPARATOR
					+ "interactionModels.idx");
			File fsaIndexFile = new File(fsaIndexFileURI);
			FileIndex fsaFileIndex = new FileIndex(fsaIndexFile);
			try {
				String id = URIUtil.parseQueryString(uri.getQuery()).get(RESOURCE_IDENTIFIER);
				String fsaFileName = fsaFileIndex.getId(id);
				IContainer[] containers = ResourcesPlugin.getWorkspace().getRoot().findContainersForLocationURI(
						fsaIndexFileURI);
				return containers[0].getParent().getFile(new Path(fsaFileName));
			} catch (FileIndexException e) {
				Logger.getInstance().log(e);
			}
		}
		return null;
	}

	public static void openTextEditorInLine(ITextEditor editor,
			int lineNumber) {
		IDocument document = editor.getDocumentProvider().getDocument(
			    editor.getEditorInput());
			  if (document != null) {
			    org.eclipse.jface.text.IRegion lineInfo = null;
			    try {
			      // line count internaly starts with 0, and not with 1 like in
			      // GUI
			      lineInfo = document.getLineInformation(lineNumber - 1);
			    } catch (BadLocationException e) {
			      // ignored because line number may not really exist in document,
			      // we guess this...
			    }
			    if (lineInfo != null) {
			      editor.selectAndReveal(lineInfo.getOffset(), lineInfo.getLength());
			    }
			  }
	}
}
