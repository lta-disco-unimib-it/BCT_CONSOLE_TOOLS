package org.cprover.launch;

import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
/**
 * @author Gérard Basler
 * @version $Revision: 1.1 $
 */
public class VerifyConfigurationTabGroup extends
		org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup {

	public VerifyConfigurationTabGroup() {
		super();
	}

	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
        this.setTabs(new ILaunchConfigurationTab[] {new VerifyMainTab(), new VerifyConfigurationTab()});	
    }
}
