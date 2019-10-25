/*
 * Created on 19.01.2009
 *
 * $author
 * 
 */
package org.cprover.communication;

public class Step {

    public Location location;

    /**
     * 
     */
    public Step() {
        super();
    }

    /**
     * @param location
     */
    public Step(Location location) {
        super();
        this.location = location;
    }

	public String toString() {
		return location.file + ": " + location.line;
	}
}
