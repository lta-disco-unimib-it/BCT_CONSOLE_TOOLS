package org.cprover.views;

import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

public class MyViewerComparator extends ViewerComparator {
	protected int column;
	protected int direction;//1|-1|0

	public MyViewerComparator(int column, int direction) {
		this.column = column;
		this.direction = direction;
	}
	
	public void nextDirection() {
		if (this.direction == 1)
			this.direction = -1;
		else if (this.direction == -1)
			this.direction = 0;
		else
			this.direction = 1;
	}
	
	public int getSWTDirection() {
		if (this.direction == 1)
			return SWT.UP;
		else if (this.direction == -1)
			return SWT.DOWN;
		else
			return SWT.NONE;
	}	
}
