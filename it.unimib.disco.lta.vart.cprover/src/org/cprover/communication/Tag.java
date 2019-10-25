package org.cprover.communication;

import java.util.HashMap;

public class Tag {
	public String name;
	public String type;
	public String value; 
	
	private HashMap<String,String> attributes = new HashMap();
	
	public Tag() {
		this.name = "";
		this.type = "";
		this.value = "";
	}

	public void setAttribute(String tagName, String tagValue) {
		attributes.put(tagName, tagValue);
	}
	
	public String getAttribute( String name ){
		return attributes.get(name);
	}
}
