package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import java.net.URI;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint.Types;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.AdvancedViolationsUtil;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.uri.URIBuilderFactory;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.editors.EditorOpener;
import modelsViolations.BctFSAModelViolation;
import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.swt.widgets.Display;

public class OpenModelViolationsCompositeAction extends ViolationsCompositeAction {
	@Override
	public String getText() {
		return "Open model";
	}

	@Override
	public String getToolTipText() {
		return "Open model";
	}

	@Override
	public String getDescription() {
		return "Open model";
	}

	@Override
	public void run(BctModelViolation violation,
			MonitoringConfiguration mc) {


		Types callPointType = MethodCallPoint.Types.ENTER;
		Method method = new Method(AdvancedViolationsUtil.getMethodName(violation));
		URI uri = null;
		String modelName = violation.getViolatedModel();

		if(violation instanceof BctFSAModelViolation) {
			FSAModel model = new FSAModel(null, method, null);
			uri = URIBuilderFactory.getBuilder(mc).buildURI(model);
		} else if(violation instanceof BctIOModelViolation) {
			IoModel model = new IoModel(method, null, null);
			uri = URIBuilderFactory.getBuilder(mc).buildURI(model);	
			if ( ! ( modelName.endsWith("ENTER") || modelName.endsWith("POINT") ) ){
				callPointType = Types.EXIT;
			}
		}

		EditorOpener.openEditor(Display.getDefault(), uri);
	}


}