package org.cprover.communication;

public class Failure extends ResultItem {
	public String reason;
	public String threadId;
	public String loopId;
	
	public Failure() {
		this.reason = "";
		this.threadId = "";
		this.loopId = "";
	}
	
	public String toString() {
		return "failure: "+ reason;
	}
}

