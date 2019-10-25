package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.DisplayNamesUtil;

import java.util.ArrayList;
import java.util.Collection;

import modelsViolations.BctModelViolation;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.IPropertySource;

public class ViolationElement implements IAdaptable {
	private ViolationPropertySource violationPS = null;
	private BctModelViolation violation;
	private String txt;
	ArrayList<ViolationElement> children;
	private MonitoringConfiguration mc;

	public ViolationElement(BctModelViolation violation, String txt, MonitoringConfiguration mc ) {
		this.mc= mc;
		this.violation = violation;
		if(violation == null) {
			this.txt = txt;
			children = new ArrayList<ViolationElement>();
		} else {
			this.txt = txt();
		}
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySource.class) {
			if (violationPS == null) {
				violationPS = new ViolationPropertySource(violation,mc);
			}
			return violationPS;
		}
		return null;
	}

	public BctModelViolation getViolation() {
		return violation;
	}

	public String getTxt() {
		return txt;
	}
	
	public Collection<ViolationElement> getChildren() {
		return children;
	}
	
	public void addChild(ViolationElement child) {
		children.add(child);
	}

	private String txt() {
		if(violation != null) {
			String tokens[] = violation.getViolatedModel().split("\\:\\:\\:");
			return DisplayNamesUtil.getProgramPointToPrint(mc, tokens[0]);
//			return ;
		}
		return null;
	}
}
