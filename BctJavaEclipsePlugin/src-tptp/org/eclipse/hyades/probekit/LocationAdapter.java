/**********************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * $Id: LocationAdapter.java,v 1.1 2011-12-01 21:34:10 pastore Exp $
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/

package org.eclipse.hyades.probekit;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * Only the probekit compiler and its editor should interact with 
 * instances of this class. The implementors reserve the right to 
 * change this class, or remove it entirely, in a future iteration 
 * without maintaining backward compatibility.
 * 
 * Instances of this class are hung off of probekit EObject instances
 * to track which line number, in the generated _probe.java file,
 * the EObject would map to. The .java line number is needed when
 * reporting JDT compile IProblem instances to the user. JDT doesn't 
 * know that the code originated from a .xml file, so JDT reports the
 * IProblem against the .java file. The probekit editor needs to tell
 * the user which of the leaves in the tree have the problem, and this
 * is done by asking the IProblem for its line number, and searching the 
 * Probekit EObjects for the LocationAdapter that matches that line number
 * the best. 
 * 
 * Instances of this class are not persisted.
 */
public class LocationAdapter extends AdapterImpl {
	public static final int LINE_NUMBER_UNSET = -1;
	private int _startLineNumber = LINE_NUMBER_UNSET;
	private int _endLineNumber = LINE_NUMBER_UNSET;
	
	/**
	 * Null object that is provided for convenience.
	 */
	public static final LocationAdapter NO_LOCATION = new LocationAdapter() {
		public int getStartLineNumber() {
			return LINE_NUMBER_UNSET;
		}
		
		public int getEndLineNumber() {
			return LINE_NUMBER_UNSET;
		}
		
		public void notifyChanged(Notification notification) {
		}
	
		public Notifier getTarget() {
			return null;
		}
	
		public void setTarget(Notifier newTarget) {
		}

		public boolean isAdapterForType(Object type) {
			return false;
		}
	};
	
	public LocationAdapter() {
	}

	/**
	 * In the .java file, which line number would the EObject begin 
	 * its code block? 
	 */
	public int getStartLineNumber() {
		return _startLineNumber;
	}
	
	/**
	 * In the .java file, which line number would the EObject complete
	 * its code block? Note that it is possible for an end line number to
	 * be equal to a start line number if the EObject spans only one line
	 * (e.g. an import statement).
	 */
	public int getEndLineNumber() {
		return _endLineNumber;
	}

	public void setStartLineNumber(int lineNumber) {
		_startLineNumber = lineNumber;
	}
	
	public void setEndLineNumber(int lineNumber) {
		_endLineNumber = lineNumber;
	}

	/**
	 * Does the EObject that this adapter is added to 
	 * span the given line number? For example, if a code block
	 * started at line 10 and ended at line 20, and the given
	 * line number was 15, then this method would return true.
	 * If the line number is less than the start line number of
	 * this block, or greater than the end line number of this block,
	 * then this method returns false.
	 * 
	 * Note that it is possible for more than one LocationAdapter
	 * to return true for a given line number. A probe, for example,
	 * contains a fragment, and a fragment contains a data item. 
	 * If the line number was the line number of the data item,
	 * then the LocationAdapters of the probe, fragment, and data
	 * item would all return true. It is the responsibility of the
	 * calling code to find the "best fit" EObject for a given line 
	 * number.
	 */
	public boolean contains(int lineNumber) {
		if(getStartLineNumber() > lineNumber) {
			return false;
		}
		
		if(getEndLineNumber() < lineNumber) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * The LocationAdapter can be added onto any EObject type.
	 */
	public boolean isAdapterForType(Object type) {
		if(type instanceof EObject) {
			return true;
		}
		return false;
	}
}
