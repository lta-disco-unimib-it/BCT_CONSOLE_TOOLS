package it.unimib.disco.lta.bct.bctjavaeclipseExtensions.ui.popup.actions;

import java.io.IOException;
import java.util.Collection;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;

import database.Registry;

public class AddTracePoint implements IEditorActionDelegate {

	private ISelection selection;
	private IEditorPart targetEditor;

	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		System.out.println(selection.getClass().getCanonicalName());
		IEditorInput input = targetEditor.getEditorInput();
		
		if ( ! ( input instanceof FileEditorInput ) ){
			
		}
		
		FileEditorInput fileInput = (FileEditorInput) input;
		IFile openFile = fileInput.getFile();
		
		System.out.println(input.getClass().getCanonicalName());
		
		ISelection editorSelection = targetEditor.getEditorSite().getSelectionProvider().getSelection();
		System.out.println(editorSelection.getClass().getCanonicalName());
		ITextSelection s = (ITextSelection) editorSelection;
		
		int startLine = s.getStartLine();
		
		Collection<MonitoringConfiguration> mcs = MonitoringConfigurationRegistry.getInstance().getMonitoringConfgurations();
		
		MonitoringConfiguration mcNames[] = new MonitoringConfiguration[mcs.size()];
		int i=0;
		for ( MonitoringConfiguration mc : mcs){
			mcNames[i++]=mc;
		}
		
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				new Shell(), new LabelProvider(){

					@Override
					public String getText(Object element) {
						MonitoringConfiguration mc = (MonitoringConfiguration) element;
						
						return mc.getConfigurationName();
					}
					
				});
		dialog.setElements(mcNames);
		dialog.setTitle("Select a MonitoringConfiguration");
		dialog.setMultipleSelection(false);
		// User pressed cancel
		if (dialog.open() != Window.OK) {
				return;
		}
		Object[] result = dialog.getResult();
		
		MonitoringConfiguration mc = (MonitoringConfiguration) result[0];
		
		CConfiguration config;
		if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
			config = (CConfiguration) mc.getAdditionalConfiguration(CRegressionConfiguration.class);	
		} else if ( mc.getConfigurationType() == MonitoringConfiguration.ConfigurationTypes.C_Config ){
			config = (CConfiguration) mc.getAdditionalConfiguration(CConfiguration.class);	
		} else {
			return;
		}
		
		try {
			config.addSourceProgramPoint( openFile, s.getStartLine() );
			MonitoringConfigurationRegistry.getInstance().save( mc );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		this.selection = selection;
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		// TODO Auto-generated method stub
		this.targetEditor = targetEditor;
	}



}
