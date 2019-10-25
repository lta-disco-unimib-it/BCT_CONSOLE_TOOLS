package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ExportDataRecordingProbeWithProgress implements
		IRunnableWithProgress {

	
	private Display display;
	private Shell shell;
	private IFile file;

	public ExportDataRecordingProbeWithProgress(Shell shell, Display display,
			IFile selectedElement) {
		this.shell = shell;
		this.display = display;
		this.file = selectedElement;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
//		try {
//			ComponentsConfiguration cc = ComponentsConfigurationDeserializer.deserialize(file.getFullPath().toFile());
//			Probe probe = ProbekitFactory.eINSTANCE.createProbe();
//			InvocationObject obj = ProbekitFactory.eINSTANCE.createInvocationObject();
//			Fragment fragment = ProbekitFactory.eINSTANCE.createFragment();
//			fragment.setType(FragmentType.ENTRY_LITERAL);
//			
//			fragment.getData().addAll();
//			probe.getFragment().add(fragment);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
