package it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.DisplayNamesUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import modelsViolations.BctIOModelViolation;
import modelsViolations.BctModelViolation;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;

import cpp.gdb.Demangler;
import cpp.gdb.TraceUtils;

public class ViolationsTableLabelAndContentProvider extends LabelProvider implements IStructuredContentProvider {
	private boolean demangle;
	private Demangler demangler;
	private MonitoringConfiguration mc;
	private boolean isCRegression = false;
	
	public ViolationsTableLabelAndContentProvider( boolean demangle ){
		this.demangle = demangle;
		if ( demangle ){
			demangler = new Demangler();
		}
	}
	
	@Override
	public String getText(Object element) {
		if (element instanceof ViolationElement) {
			ViolationElement ve = (ViolationElement) element;
			BctModelViolation violation = ve.getViolation();
			
			String model = violation.getViolatedModel();
			String violationDescription = violation.getViolation();
			String violationType = violation.getViolationType();
			
			if ( demangle ){
				try {
				int originalSignatureLen;
				String originalModelName = model;
				String prefix = "";
				
					
				if ( violation instanceof BctIOModelViolation ){
					if ( model.endsWith(":::ENTER") ){
						originalSignatureLen = model.length() - 9;
						model = demangler.demangle( model );
						prefix = Prefixes.ENTER;
					} else if ( model.endsWith(":::EXIT") ){
						originalSignatureLen = model.length() - 8;
						prefix = Prefixes.EXIT;
					} else {
						originalSignatureLen = model.length();
					}
					
					violationDescription = violation.getViolation();
					
					violationDescription = violationDescription.replace("returnValue.eax", "return");
				} else {
					originalSignatureLen = model.trim().length();
					violationDescription = violation.getViolation();
					prefix =Prefixes.EXEC;
					if ( BctModelViolation.ViolationType.UNEXPECTED_TERMINATION.equals( violation.getViolationType() ) || 
							BctModelViolation.ViolationType.UNEXPECTED_TERMINATION_SEQUENCE.equals( violation.getViolationType() )){
						
					} else {
						String event = violation.getViolation().trim();
						String newEventName = TraceUtils.getFunctionName( event );
						
						String demangledEvent = demangler.demangle(newEventName);
						demangledEvent = DisplayNamesUtil.getSignatureToPrint(mc, demangledEvent);
						if ( newEventName.length() < event.length() ){
							System.err.println("event "+event);
							System.err.println("newEvent "+newEventName);
							demangledEvent += event.substring(newEventName.length() );
							
						} else {
							
						}
						violationDescription = demangledEvent;
						
						if ( isCRegression ){
							violationType = "UNEXPECTED INSTRUCTION";
						}
					}
				}
				String modelWithLine = model.substring( 0, originalSignatureLen);
				
				model = TraceUtils.getFunctionName( modelWithLine );
				int newLen = model.length();
				
				model = demangler.demangle(model );
				model = DisplayNamesUtil.getSignatureToPrint(mc, model);
				
				String suffix;
				if ( newLen != originalSignatureLen ){
					suffix = originalModelName.substring(newLen, originalSignatureLen);
					prefix = Prefixes.BEFORE_LINE;
				} else {
					suffix = "";
				}
				
				model = prefix + model + suffix;
				} catch ( Throwable t ) {
					t.printStackTrace();
				}
			}
			
			
			return  model  + " " + violationType + ": " + violationDescription;
		}
		return super.getText(element);
	}

	public static class Prefixes {
		public static final String EXEC = 			"EXECUTING   ";
		public static final String EXIT = 			"EXITING     ";
		public static final String BEFORE_LINE = 	"BEFORE LINE ";
		public static final String ENTER = 			"ENTERING    ";
	}
	
	
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		Collection<ViolationElement> inputViolationsElements = (Collection<ViolationElement>) inputElement;
		List<ViolationElement> violationsElements = new ArrayList<ViolationElement>();
		
		// We want add to table only "real" violation elements, not violation elements used in tree view to
		// group violations.
		for (ViolationElement ve : inputViolationsElements) {
			if (ve.getViolation() != null) {
				violationsElements.add(ve);
			}
		}
		return violationsElements.toArray();
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// We don't provide support to input changes.
	}

	public void setMonitoringConfiguration(MonitoringConfiguration mc) {
		this.mc = mc;
		if ( mc != null && mc.getConfigurationType() ==  MonitoringConfiguration.ConfigurationTypes.C_Regression_Config ){
			isCRegression = true;
		}
	}
}
