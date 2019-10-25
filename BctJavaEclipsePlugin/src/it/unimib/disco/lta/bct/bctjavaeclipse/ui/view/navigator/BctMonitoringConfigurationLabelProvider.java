package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class BctMonitoringConfigurationLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof BctMonitoringConfigurationTreeData) {		
			return ((BctMonitoringConfigurationTreeData) element).getImage();
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof BctMonitoringConfigurationTreeData) {
			return ((BctMonitoringConfigurationTreeData) element).getTxtToDisplay();
		}
		return null;
	}
}
