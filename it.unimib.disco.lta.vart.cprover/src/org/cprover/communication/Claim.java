/**
 * 
 */
package org.cprover.communication;

import org.cprover.runners.ClaimCheckRunner;

public class Claim extends ResultItem {
	public String id;
	public String property;
	public String description;
	public String expression;
	public ClaimCheckRunner clRun;
	
	public Claim() {
		this.property = "";
		this.description = "";
		this.expression = "";
		this.clRun = null;
	}

	public String toString() {
		return description+ " : "+ expression+" ("+location+")";
	}	
}