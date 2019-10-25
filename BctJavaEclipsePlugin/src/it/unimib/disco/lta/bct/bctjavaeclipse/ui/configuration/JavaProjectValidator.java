package it.unimib.disco.lta.bct.bctjavaeclipse.ui.configuration;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;

public class JavaProjectValidator extends ProjectValidator {
	private String projectNatureFilter = JavaCore.NATURE_ID;
	
	@Override
	protected String accept(IProject project) {
		try {
			
			
			
			if ( project.getNature(projectNatureFilter) != null ){
				return null;
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
