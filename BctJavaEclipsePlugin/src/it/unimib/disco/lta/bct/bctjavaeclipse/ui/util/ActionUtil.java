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
