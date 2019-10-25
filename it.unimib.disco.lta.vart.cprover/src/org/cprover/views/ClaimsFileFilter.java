package org.cprover.views;

import org.cprover.communication.Claim;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class ClaimsFileFilter extends ViewerFilter {
	public String file;

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return ((Claim)element).location.file.indexOf(this.file) >= 0;
	}

	public ClaimsFileFilter() {
		this.file = "";
	}
}
