package it.unimib.disco.lta.bct.bctjavaeclipse.ui.vart;

import java.util.Comparator;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTDataProperty;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

public class VartDataPropertiesComparator extends ViewerComparator implements Comparator<VARTDataProperty> {

	private int direction;
	private int propertyIndex = 0;

	public int getDirection() {
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.propertyIndex = column;
			direction = 1;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		VARTDataProperty l = (VARTDataProperty) e1;
		VARTDataProperty r = (VARTDataProperty) e1;

		return compare(l, r);
	}

	@Override
	public int compare(VARTDataProperty l, VARTDataProperty r) {

		switch ( propertyIndex ) {
		case 4:
			return l.getResult().compareTo(r.getResult());
		}

		int fComp = l.getFile().compareTo(r.getFile());
		if ( fComp != 0 ){
			return fComp;
		}

		int lComp = Integer.compare(l.getInjectedLine(), r.getInjectedLine() );
		if ( lComp != 0 ){
			return lComp;
		}


		return l.getAssertion().compareToIgnoreCase(r.getAssertion());

	}



}
