package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class InstrumentDataRecordingProbe  implements IObjectActionDelegate  {

	public InstrumentDataRecordingProbe() {
		// TODO Auto-generated constructor stub
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}

	public void run(IAction action) {
		ISelection selection;
		selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		final Display display = PlatformUI.getWorkbench().getDisplay();
		final Shell shell = display.getActiveShell();

		
//		if ( selection instanceof IStructuredSelection ){
//			IStructuredSelection ss = (IStructuredSelection) selection;
//			final IFile selectedElement = (IFile) ss.getFirstElement();
//			InstrumentDataRecordingProbeWithProgress runInstrument = 
//				new InstrumentDataRecordingProbeWithProgress(
//						shell,
//						display,
//						selectedElement
//				);
//			
//			ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(shell);
//			try {
//				progressMonitorDialog.run(true, true, runInstrument);
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
