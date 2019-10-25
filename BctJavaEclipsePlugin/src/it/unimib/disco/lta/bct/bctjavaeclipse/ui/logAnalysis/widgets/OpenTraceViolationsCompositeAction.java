package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import java.net.URI;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint.Types;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIBuilderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorOpener;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import cpp.gdb.LineData;

public class OpenTraceViolationsCompositeAction extends ViolationsCompositeAction {


		@Override
		public String getText() {
			return "Open Trace";
		}

		@Override
		public String getToolTipText() {
			return "Open Trace";
		}

		@Override
		public String getDescription() {
			return "Open Trace";
		}
		
		@Override
		public void run(BctModelViolation violation,
				MonitoringConfiguration mc) {
			// TODO Auto-generated method stub
						Types callPointType = MethodCallPoint.Types.ENTER;
						String modelName = violation.getViolatedModel();
						if(violation instanceof BctIOModelViolation) {
							if ( ! ( modelName.endsWith("ENTER") || modelName.endsWith("POINT") ) ){
								callPointType = Types.EXIT;
							}
						}

						try {
						URI uri = URIBuilderFactory.getBuilder(mc).buildRawTraceURI(violation.getPid(), violation.getThreadId(), violation.getCallId(), callPointType );
						EditorOpener.openEditor(Display.getDefault(), uri);
						} catch ( Throwable t ){
							t.printStackTrace();
						}
			

		}

		
	}