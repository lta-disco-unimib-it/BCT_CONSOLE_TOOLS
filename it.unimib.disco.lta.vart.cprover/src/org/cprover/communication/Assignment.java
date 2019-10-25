package org.cprover.communication;

public class Assignment extends Step {
	public String identifier;
	public String value;
	public String type;
	public String threadId;
	
	public Assignment(Location location, String identifier, String value, String type, String threadId) {
		super(location);
		this.identifier = identifier;
		this.value = value;
		this.type = type;
		this.threadId = threadId;
	}

	public Assignment() {
		this.identifier = "";
		this.value = "";
		this.type = "";
		this.threadId = "";
	}
	
	public String toString() {
		return (identifier.equals(""))? value: (identifier+ " = "+ value);
	}
}

