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
