package org.cprover.communication;

import java.util.LinkedList;

public class Trace {
	
	/**
	 * Every step contains one assignment. 
	 */
	public LinkedList<Step> steps = new LinkedList<Step>();
	public Failure failure;
	
	public Trace() {
	}	
	
	public void clear() {
		this.steps.clear();
		this.failure = null;
	}
}

