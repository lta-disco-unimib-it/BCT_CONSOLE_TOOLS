package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionRawTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.DisplayNamesUtil;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import util.componentsDeclaration.SystemElement;

public class DomainObjectsLabelProvider implements ILabelProvider {

	private MonitoringConfiguration mc;

	public DomainObjectsLabelProvider(MonitoringConfiguration mc) {
		this.mc = mc;
	}

	public DomainObjectsLabelProvider() {
		// TODO Auto-generated constructor stub
	}

	public Image getImage(Object element) {
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		
		if(element instanceof BctMonitoringConfigurationTreeData) {
			return sharedImages.getImage(ISharedImages.IMG_OBJ_FOLDER);
		} else if (element instanceof SystemElement) {
			return sharedImages.getImage(ISharedImages.IMG_OBJ_FOLDER);
		}
		return sharedImages.getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}

	public String getText(Object element) {
		String text = null;
		Method method = Method.getMethod(element);
		
		if (method != null) {
			text = DisplayNamesUtil.getSignatureToPrint(mc, method.getSignature());
		} else {
			if (element instanceof InteractionRawTrace) {
				InteractionRawTrace trace = (InteractionRawTrace) element;
				text = "Session "+trace.getThread().getSessionId()+" Thread " + trace.getThread().getThreadId();
			}
		}
		return text;
	}

	// We will use only getImage() and getText() methods, so we will not provide
	// the implementations of following methods.
	
	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}
}
