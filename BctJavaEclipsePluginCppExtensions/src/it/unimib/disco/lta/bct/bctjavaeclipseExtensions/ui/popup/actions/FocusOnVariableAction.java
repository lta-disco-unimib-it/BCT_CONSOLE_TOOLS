package it.unimib.disco.lta.bct.bctjavaeclipseExtensions.ui.popup.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.MonitoringConfigurationRegistry;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.VARTRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.serialization.MonitoringConfigurationSerializer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.part.FileEditorInput;

import database.Registry;

public class FocusOnVariableAction implements IEditorActionDelegate {

	private ISelection selection;
	private IEditorPart targetEditor;

	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		
		System.out.println(selection.getClass().getCanonicalName());
		
		String varName="";
		if ( selection instanceof TextSelection ){
			TextSelection sel = (TextSelection) selection;
			varName = sel.getText();
		} else if ( ( selection instanceof StructuredSelection ) ){
			
		
		StructuredSelection ssel = (StructuredSelection)selection;
		Object _input = ssel.getFirstElement();
		if ( !( _input instanceof FileEditorInput ) ){
			return;
		}
		
		IEditorInput input = (IEditorInput) _input;
		
		if ( ! ( input instanceof FileEditorInput ) ){
			
		}
		
		if ( targetEditor == null ){
			targetEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		}
		
		FileEditorInput fileInput = (FileEditorInput) input;
		IFile openFile = fileInput.getFile();
		
		
		ISelection editorSelection = targetEditor.getEditorSite().getSelectionProvider().getSelection();
		System.out.println(editorSelection.getClass().getCanonicalName());
		ITextSelection s = (ITextSelection) editorSelection;
		System.out.println("SELECTED TEXT: "+s);
		int startLine = s.getStartLine();
		
		
		
		
		try {
		InputStream is = openFile.getContents();
		char c;
		int i = 0;
		
		int start = s.getOffset();
		int end = start+s.getLength()-1;
		
			while ( ( c = (char) is.read() ) != -1 ){
				if ( i >= start ){
					varName+=c;
				}
				if ( i > end ){
					break;
				}
				i++;
					
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
		System.out.println("VARNAME "+varName);
		
		Collection<MonitoringConfiguration> mcs = MonitoringConfigurationRegistry.getInstance().getMonitoringConfgurations();
		
		MonitoringConfiguration mcNames[] = new MonitoringConfiguration[mcs.size()];
		int i=0;
		for ( MonitoringConfiguration mc : mcs){
			mcNames[i++]=mc;
		}
		
		if ( mcNames.length == 0 ){
			return;
		}
		
		Object[] result;
		if ( mcNames.length == 1 ){
			result = new Object[]{mcNames[0]};
		} else  {
		
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
		result = dialog.getResult();
	}
		MonitoringConfiguration mc = (MonitoringConfiguration) result[0];
		
		VARTRegressionConfiguration vartConfig = (VARTRegressionConfiguration) mc.getAdditionalConfiguration(VARTRegressionConfiguration.class);
		vartConfig.addVariableToCheck( varName );
		try {
			MonitoringConfigurationRegistry.getInstance().save( mc );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.targetEditor = targetEditor;
	}



}
