package it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions;

import java.util.ArrayList;
import java.util.List;

import it.unimib.disco.lta.ava.alignment.StringsAlignmentPreciseGap;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.ui.view.navigator.actions.CallPointDifferences.Difference;

public class TracesComparator {
	
	private List<MethodCallPoint> firstSequence, secondSequence;
	private List<String> firstSequenceAligned, secondSequenceAligned;
	private static final String GAP = StringsAlignmentPreciseGap.EMPTY_STRING;
	
	public TracesComparator(List<MethodCallPoint> firstSequence, List<MethodCallPoint> secondSequence, 
			List<String> firstSequenceAligned, List<String> secondSequenceAligned) {
		this.firstSequence = firstSequence;
		this.secondSequence = secondSequence;
		this.firstSequenceAligned = firstSequenceAligned;
		this.secondSequenceAligned = secondSequenceAligned;
	}
	
	public List<String> getFirstSequenceAligned() {
		return firstSequenceAligned;
	}
	
	public List<String> getSecondSequenceAligned() {
		return secondSequenceAligned;
	}
	
	
	public List<List<Difference>> getDifferences() {
		List<List<Difference>> differences = new ArrayList<List<Difference>>();
		int indexTrace1 = 0, indexTrace2 = 0;
		
		for (int i = 0; i < firstSequenceAligned.size(); i++) {
			String signature1 = firstSequenceAligned.get(i), signature2 = secondSequenceAligned.get(i);
			
			if (signature1.equals(GAP) || signature2.equals(GAP)) { // One of call points is a gap.
				if (!signature1.equals(GAP))
					indexTrace1++;
				if (!signature2.equals(GAP))
					indexTrace2++;
				continue; 
			} else {
				MethodCallPoint cp1 = firstSequence.get(indexTrace1);
				MethodCallPoint cp2 = secondSequence.get(indexTrace2);
				indexTrace1++;
				indexTrace2++;
				
				if (cp1.isEnter() && cp2.isExit()) { 
					continue;
				} 
				
				if (cp2.isEnter() && cp1.isExit()) { 
					continue;
				} 

				CallPointDifferences cpd = new CallPointDifferences(cp1, cp2);
				differences.add(cpd.getDifferences());

			}
		}
		
		return differences;
	}
	
	
	/**
	 * @return A list of differences for each call point. A null value means that no differences was found among
	 * two call points.
	 */
	public List<List<Difference>> getDifferencesWithNullFill() {
		List<List<Difference>> differences = new ArrayList<List<Difference>>(firstSequenceAligned.size());
		int indexTrace1 = 0, indexTrace2 = 0;
		
		for (int i = 0; i < firstSequenceAligned.size(); i++) {
			String signature1 = firstSequenceAligned.get(i), signature2 = secondSequenceAligned.get(i);
			
			if (signature1.equals(GAP) || signature2.equals(GAP)) { // One of call points is a gap.
				if (!signature1.equals(GAP))
					indexTrace1++;
				if (!signature2.equals(GAP))
					indexTrace2++;
				differences.add(i, null);
				continue; 
			} else {
				MethodCallPoint cp1 = firstSequence.get(indexTrace1);
				MethodCallPoint cp2 = secondSequence.get(indexTrace2);
				indexTrace1++;
				indexTrace2++;
				
				if (cp1.isEnter() && cp2.isExit()) {
					differences.add(i, null);
					continue;
				} 
				
				if (cp2.isEnter() && cp1.isExit()) {
					differences.add(i, null);
					continue;
				} 

				CallPointDifferences cpd = new CallPointDifferences(cp1, cp2);
				differences.add(i, cpd.getDifferences());

			}
		}
		
		return differences;
	}

}
