package org.cprover.communication;

import java.util.Vector;

public class Transition {
	public Vector<Assignment> assignments;
	public String timeFrame;

	public Transition() {
		this.assignments = new Vector<Assignment>();
		this.timeFrame = "";
	}
	
	public String getValue(String id) {
		Assignment asgn = this.getAssignment(id);
		if (asgn == null)
			return "";
		return asgn.value;
	}
	
	public String getType(String id) {
		Assignment asgn = this.getAssignment(id);
		if (asgn == null)
			return "";
		return asgn.type;
	}
	
	public int[] getRange(String id) {
		int n = 0;
		int k = 0;
		String[] tokens = this.getType(id).split("[\\[:\\]]");		
		try {
			if (tokens.length > 1)
				n = Integer.parseInt(tokens[1]);
			if (tokens.length > 2)
				k = Integer.parseInt(tokens[2]);			
		}
		catch (Exception e) {
		}
		return new int[]{n, k};
	}
	
	public String getBit(String id, int index) {
		Assignment asgn = this.getAssignment(id);
		if (asgn == null)
			return "";
		String value = asgn.value;
		long val;
		if (index > 63)
			return "X";
		try {
			val = Long.parseLong(value);
			if ((val & (1l << index)) != 0)
				return "1";
			else
				return "0";
		}
		catch (Exception e) {
		}
		String bits = this.getBits(value); 
		if (!bits.equals("")) {
			if (index < bits.length()) 
				return String.valueOf(bits.charAt(bits.length()- index- 1));
			else
				return "0";
		}
		return "X";
	}
	
	private String getBits(String hexaStr) {
		if (!hexaStr.startsWith("'h"))
			return "";
		
		String str = hexaStr.substring(2).toUpperCase();
		StringBuffer sbuf = new StringBuffer();
		
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
			case '0':
				sbuf.append("0000");
				break;
			case '1':
				sbuf.append("0001");
				break;
			case '2':
				sbuf.append("0010");
				break;
			case '3':
				sbuf.append("0011");
				break;
			case '4':
				sbuf.append("0100");
				break;
			case '5':
				sbuf.append("0101");
				break;
			case '6':
				sbuf.append("0110");
				break;
			case '7':
				sbuf.append("0111");
				break;
			case '8':
				sbuf.append("1000");
				break;
			case '9':
				sbuf.append("1001");
				break;
			case 'A':
				sbuf.append("1010");
				break;
			case 'B':
				sbuf.append("1011");
				break;
			case 'C':
				sbuf.append("1100");
				break;
			case 'D':
				sbuf.append("1101");
				break;
			case 'E':
				sbuf.append("1110");
				break;
			case 'F':
				sbuf.append("1111");
				break;
			}
		}
		
		return sbuf.toString();
	}
	
	public Vector<String> getIds(String filter) {
		Vector<String> ids = new Vector<String>();
		for (Assignment a: this.assignments) {
			if (a.identifier.indexOf(filter) >= 0)
				ids.add(a.identifier);
		}
		return ids;
	}
	
	private Assignment getAssignment(String id) {
		for (Assignment a: this.assignments) {
			if (a.identifier.equals(id))
				return a;
		}
		return null;		
	}
}
