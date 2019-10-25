package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint.Types;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.CRegressionConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.AdvancedViolationsUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTDataProperty;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorOpener;

import java.io.File;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import cpp.gdb.LineData;

public class OpenSourceFileVARTPropertyAction extends Action {
	private MonitoringConfiguration mc;
	private VARTDataProperty property;

	@Override
	public String getText() {
		return "Open Source File";
	}

	@Override
	public String getToolTipText() {
		return "Open Source File";
	}

	@Override
	public String getDescription() {
		return "Open Source File";
	}
	
	public OpenSourceFileVARTPropertyAction( MonitoringConfiguration mc, VARTDataProperty property ){
		this.mc = mc;
		this.property = property;
	}

	@Override
	public void run() {


		try {
		final IFile file = property.getIFile();
		
		
		if ( file == null ){
			return;
		}
		Logger.getInstance().logInfo("Opening file: "+file.getLocation().toString(),null);
		System.out.println("Trying to open: "+file.getLocation().toString());
		
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IEditorPart editor = IDE.openEditor(page, file);
					System.out.println(editor.getClass().getCanonicalName());
					if ( editor instanceof ITextEditor ){
						System.out
						.println("TEXT");
						ITextEditor textEditor = (ITextEditor) editor;
						EditorOpener.openTextEditorInLine( textEditor , property.getLine() );
					}
					System.out.println(editor.getClass());
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});	

		return;
		} catch ( Throwable t ){
			t.printStackTrace();
			return;
		}
	}


}