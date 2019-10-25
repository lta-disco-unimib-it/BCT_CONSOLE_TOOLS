/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.bct.bctjavaeclipse.ui.vart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.vart.VARTDataProperty;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import tools.fshellExporter.CBMCExecutor.ValidationResult;
import tools.violationsAnalyzer.MultiHashMap;

public class VARTMarker {
	private static final HashSet<IFile> markedFiles = new HashSet<>();
	public static final VARTMarker INSTANCE = new VARTMarker();
	private static boolean SET_ALL_LINE_MARKERS_INVALID_IN_CASE_AT_LEAST_ONE_IS_INVALID = false;
	
	
	
	public void cleanupMarkers(){
		currentView = null;
		for (IFile f : markedFiles ) {
			try {
				f.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		markedFiles.clear();
		
		linesWithErrors = new MultiHashMap<>();
		
		System.out.println("CLEANED");
	}
	
	public void mark(VARTDataProperty dp, int markerSeverityForInvalid, int outdatedSeverity) {
		System.out.println("ERROR SEVERITY "+markerSeverityForInvalid);
		
		IFile file = dp.getIFile();
		
		
		markedFiles.add(file);
		IMarker m;
		try {
			m = file.createMarker(IMarker.PROBLEM);
			int line = dp.getLine();
			m.setAttribute(IMarker.LINE_NUMBER, line);
			m.setAttribute(IMarker.MESSAGE, dp.getResult()+" "+dp.getAssertion());
			m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			
			
			int severity;
			if ( dp.getResult() == ValidationResult.INVALID || dp.getResult() == ValidationResult.UNREACHABLE ){
				severity = markerSeverityForInvalid;
			} else if ( dp.getResult() == ValidationResult.OUTDATED ){
				severity = outdatedSeverity;
			} else {
				severity = IMarker.SEVERITY_INFO;
			}
			
			if ( SET_ALL_LINE_MARKERS_INVALID_IN_CASE_AT_LEAST_ONE_IS_INVALID ){
				List<Integer> lines = linesWithErrors.get(file);
				if ( lines != null ){
					if ( lines.contains(line )){
						System.out.println("HAS ERROR");
						severity = markerSeverityForInvalid;
					}
				}
			}
			
			System.out.println("!MARKING "+line+" "+severity+" errSev:"+markerSeverityForInvalid);
			m.setAttribute(IMarker.SEVERITY, severity);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	}

	MultiHashMap<IFile,Integer > linesWithErrors = new MultiHashMap<IFile,Integer>();
	private VartDataPropertiesView currentView;
	public void markAll(Collection<VARTDataProperty> loadedResults, int invalidSeverity, int outdatedSeverity) {
		System.out.println("ERROR SEVERITY: "+invalidSeverity);

		
		for ( VARTDataProperty dp : loadedResults ){
			if ( dp.getResult() == ValidationResult.INVALID ){
				linesWithErrors.put( dp.getIFile(), dp.getLine() );
			}
		}
		
		LinkedList<VARTDataProperty> sorted = new LinkedList<>();
		sorted.addAll(loadedResults);
		
		Collections.sort(sorted, new Comparator<VARTDataProperty>(){

			@Override
			public int compare(VARTDataProperty o1, VARTDataProperty o2) {

				int delta = o1.getLine() - o2.getLine();
				if ( delta == 0 ){
					int s1 = calculateSeverity(o1);
					int s2 = calculateSeverity(o2);

					return s1-s2;
				}
				
				return delta;
			}

			private int calculateSeverity(VARTDataProperty dp) {
				int severity;
				if ( dp.getResult() == ValidationResult.INVALID || dp.getResult() == ValidationResult.UNREACHABLE ){
					severity = 1;
				} else if ( dp.getResult() == ValidationResult.OUTDATED ){
					severity = 2;
				} else {
					severity = 3;
				}
				return severity;
			}
			
		});
		
		for ( VARTDataProperty dp : loadedResults ){
			mark( dp, invalidSeverity, outdatedSeverity );
		}
	}

	public void markAll(Collection<VARTDataProperty> properties) {
		markAll(properties, IMarker.SEVERITY_ERROR, IMarker.SEVERITY_WARNING );
	}

	public boolean cleanupMarkers(VartDataPropertiesView propertiesView) {
		if ( currentView == propertiesView ){
			return false;
		}
		cleanupMarkers();
		currentView = propertiesView;
		return true;
	}

	
}
