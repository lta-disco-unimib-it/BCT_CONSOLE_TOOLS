/**
 * 
 */
package org.cprover.communication;

/**
 * Base class for XML communication.
 * 
 * @author Gérard Basler
 */
public class ResultItem {
	public Location location;

	public ResultItem() {
		
	}
	
	public ResultItem(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return location.toString();
	}	

	
}