package it.unimib.disco.lta.bct.bctjavaeclipse.ui.popup.actions;

import it.unimib.disco.lta.bct.bctjavaeclipse.ui.BctJavaEclipsePlugin;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.BctLogAnalysisView;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.hyades.models.hierarchy.TRCAgentProxy;
import org.eclipse.hyades.trace.ui.HyadesUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class OpenBctLogAnalysisViewAction extends
		org.eclipse.hyades.trace.ui.actions.OpenAssociatedTraceViewAction {

	public OpenBctLogAnalysisViewAction() {
		// TODO Auto-generated constructor stub
	}

	public OpenBctLogAnalysisViewAction(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public OpenBctLogAnalysisViewAction(String text, ImageDescriptor image) {
		super(text, image);
		// TODO Auto-generated constructor stub
	}

	public OpenBctLogAnalysisViewAction(String text, int style) {
		super(text, style);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getViewID() {
		// TODO Auto-generated method stub
		throw new sun.reflect.generics.reflectiveObjects.NotImplementedException();
	}
	
	public void run() {
        IWorkbenchWindow workbenchWindow = 
            BctJavaEclipsePlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
        try {
            IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
            BctLogAnalysisView view =
                (BctLogAnalysisView) workbenchPage.showView("it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.bctLogAnalysisView");
            
            
            //get the mof
            
            EObject eObj = HyadesUtil.getMofObject(); // Fetching data
            EObject mofObject = HyadesUtil.getObjectToView(HyadesUtil.getMofObject());
            
//            System.out.println("-- "+mofObject.toString());
//            for ( EAttribute a : mofObject.eClass().getEAllAttributes()){
//            	System.out.println("-- " +a.getName()   );
//            }
//            TreeIterator<EObject> it = mofObject.eAllContents();
//            while(it.hasNext()){
//            	EObject obj = it.next();
//            	System.out.println("-- "+obj);
//            }
           //FIXME: retrieve the log content in the standard way. this is an HACK! 
           TRCAgentProxy proxy = (TRCAgentProxy) mofObject;
           System.out.println();
           File file = new File(proxy.getName().substring(26));
           ArrayList<File> files = new ArrayList<File>();
           files.add(file);
           view.setFiles(files);
           System.out.println("SETTED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
