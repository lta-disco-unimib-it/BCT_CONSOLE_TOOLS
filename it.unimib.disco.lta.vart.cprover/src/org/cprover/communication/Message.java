package org.cprover.communication;

public class Message extends ResultItem {
	public final String type;
	public String text;
	
	public Message(String type) {
		this.type = type;
		this.text = "";
	}
	
	public String toString() {
		return text;
	}
}

