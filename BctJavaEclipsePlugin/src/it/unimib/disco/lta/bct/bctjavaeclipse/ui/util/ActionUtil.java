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
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.util;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationDeserializer;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.BctJavaEclipsePlugin;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ActionUtil {

	public static MonitoringConfiguration getSelectedMonitoringConfiguration(IAction action) throws CoreException {
		try {
			return MonitoringConfigurationDeserializer.deserialize(getSelectedMonitoringConfigurationFile(action));
		} catch (FileNotFoundException e) {
			throw new CoreException(new Status(IStatus.ERROR, BctJavaEclipsePlugin.PLUGIN_ID, "Cannot read configuration file.", e) );
		}
	}
	
	public static File getSelectedMonitoringConfigurationFile(IAction action) throws CoreException {

		// TODO Auto-generated method stub
		final ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();

		if ( selection == null ){
			throw new CoreException(new Status(IStatus.ERROR, BctJavaEclipsePlugin.PLUGIN_ID, "Nothing was selected" ) );
		}
		
		if ( selection instanceof IStructuredSelection ){

			IStructuredSelection ss = (IStructuredSelection) selection;
			IFile selectedElement = (IFile) ss.getFirstElement();
			File selectedFile = selectedElement.getLocation().toFile();
			

			return selectedFile;
			
			
		}
		
		throw new CoreException(new Status(IStatus.ERROR, BctJavaEclipsePlugin.PLUGIN_ID, "Unknown selection type: "+selection.getClass().getCanonicalName() ) );
	}

}
