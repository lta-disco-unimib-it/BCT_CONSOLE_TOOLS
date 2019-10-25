package org.cprover.communication;

import org.cprover.core.CProverPlugin;
import org.cprover.ui.Constants;
import org.eclipse.swt.graphics.Image;

public enum Result {
	UNKNOWN(Constants.ICON_UNKNOWN),
	SUCCESS(Constants.ICON_SUCCESS),
	FAILURE(Constants.ICON_FAILURE),
	ERROR(Constants.ICON_ERROR);

	private String icon;

	private Result(String icon) {
		this.icon = icon;
	}
	
	public Image getImage() {
		return CProverPlugin.getImageCache().get(icon);
	}
}
