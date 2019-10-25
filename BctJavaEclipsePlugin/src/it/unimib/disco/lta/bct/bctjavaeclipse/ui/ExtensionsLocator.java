package it.unimib.disco.lta.bct.bctjavaeclipse.ui;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.logAnalysis.run.ViolationsFilterAction;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.logAnalysis.widgets.ViolationsCompositeAction;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class ExtensionsLocator {

	private static List<ViolationsFilterAction> violationFilterActions;

	public static List<ViolationsCompositeAction> getViolationCompositeActions(){
		IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(ExtensionPoints.VIOLATIONS_COMPOSITE_ACTIONS);
	       
        ArrayList<ViolationsCompositeAction> result = new ArrayList<ViolationsCompositeAction>();
       
        for ( IConfigurationElement  ce : configElements ){
            try {
                Object exec = ce.createExecutableExtension("class");
               
                result.add((ViolationsCompositeAction) exec );
            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
       
        return result;
	}
	
	public static List<ViolationsFilterAction> getViolationFilterActions(){
		
		if ( violationFilterActions == null ){
			IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(ExtensionPoints.VIOLATIONS_FILTER_ACTIONS);

			ArrayList<ViolationsFilterAction> result = new ArrayList<ViolationsFilterAction>();

			for ( IConfigurationElement  ce : configElements ){
				try {
					Object exec = ce.createExecutableExtension("class");

					result.add((ViolationsFilterAction) exec );
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			violationFilterActions = result;
		}

		return violationFilterActions;
	}
}
