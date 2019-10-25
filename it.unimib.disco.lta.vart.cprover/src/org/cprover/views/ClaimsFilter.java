package org.cprover.views;

import java.util.HashSet;
import java.util.Set;

import org.cprover.communication.Claim;
import org.cprover.communication.Result;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class ClaimsFilter extends ViewerFilter {	
	public Set<String> items;

	public ClaimsFilter() {
		this.items = new HashSet<String>();
		this.items.add("All");
		this.items.add("Status");
		this.items.add("Property");
		this.items.add("Running");
		this.items.add("Waiting");
		this.items.add("Success");
		this.items.add("Unknown");
		this.items.add("Error");
		this.items.add("Failure");
		this.items.add("array bounds");
		this.items.add("pointer dereference");
		this.items.add("overflow");
		this.items.add("division-by-zero");
		this.items.add("assertion");
	}

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Claim claim = (Claim)element;
		boolean bStatus = false;
		boolean bProp = false;
		
		if (items.contains("All"))
			return true;
		
		if (items.contains("Status"))
			bStatus = true;
		
		if (items.contains("Property"))
			bProp = true;
		
		if (items.contains("Running")) {
			if (claim.clRun.isRunning())
				bStatus = true;
		}
		if (items.contains("Waiting")) {
			if (claim.clRun.waiting)
				bStatus = true;
		}
		if (items.contains("Success")) {
			if (claim.clRun.result == Result.SUCCESS)
				bStatus = true;
		}
		if (items.contains("Unknown")) {
			if (claim.clRun.result == Result.UNKNOWN && !claim.clRun.isRunning() && !claim.clRun.waiting)
				bStatus = true;
		}
		if (items.contains("Error")) {
			if (claim.clRun.result == Result.ERROR)
				bStatus = true;
		}
		if (items.contains("Failure")) {
			if (claim.clRun.result == Result.FAILURE)
				bStatus = true;
		}
		if (items.contains("array bounds")) {
			if (claim.property.equals("array bounds"))
				bProp = true;
		}
		if (items.contains("pointer dereference")) {
			if (claim.property.equals("pointer dereference"))
				bProp = true;
		}
		if (items.contains("overflow")) {
			if (claim.property.equals("overflow"))
				bProp = true;
		}
		if (items.contains("division-by-zero")) {
			if (claim.property.equals("division-by-zero"))
				bProp = true;
		}
		if (items.contains("assertion")) {
			if (claim.property.equals("assertion"))
				bProp = true;
		}
		
		return bStatus && bProp;
	}

}
