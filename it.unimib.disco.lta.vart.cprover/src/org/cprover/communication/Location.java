package org.cprover.communication;

/**
 * Stores locations of messages.
 * 
 * @author Gérard Basler
 */
public class Location {
	public String file;
	public int line;
	public int column;
	
	public Location(String file, int line) {
		this.file = file;
		this.line = line;
		this.column = 0;
	}

	public Location(String file) {
		this.file = file;
		this.line = 1;
		this.column = 0;
	}

	public Location() {
		this.file = "";
		this.line = -1;
		this.column = 0;
	}
	
	public String toString() {
		return file+ ":"+ line;
	}

	public boolean equals(Object o) {
		if ( o == null || getClass() != o.getClass() ) return false;
		
		Location other = (Location)o;
		return this.file.equals(other.file) && this.line == other.line;
	}

    @Override
    public int hashCode() {
        return file.hashCode() ^ line;
    }
}