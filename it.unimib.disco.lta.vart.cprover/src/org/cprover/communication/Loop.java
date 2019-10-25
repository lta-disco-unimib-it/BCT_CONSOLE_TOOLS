package org.cprover.communication;

public class Loop extends ResultItem {
	public String id;
	public int bound;
	public boolean boundTooSmall;
	
	public Loop() {
		this.id = "";
		this.bound = -1;
		this.boundTooSmall = false;
	}
	
	public String toString() {
		return this.id;
	}
}
