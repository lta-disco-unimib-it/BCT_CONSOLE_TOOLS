package fsa.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import fsa.FSA;

/**
 * This class permits to import/export FSA to file
 * 
 * @author Fabrizio Pastore
 *
 */
public class FSAExporter {

	
	/**
	 * Export the automaton to a file in xmi format
	 * 
	 * @param dest	file to export the automanton to
	 * @param fsa	the automaton to store in the file
	 */
	public void exportToXMIFile( File dest, FSA fsa ) {
		  // Create a resource set.
		  ResourceSet resourceSet = new ResourceSetImpl();

		  // Register the default resource factory -- only needed for stand-alone!
		  resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
		    Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		  // Get the URI of the model file.
		  URI fileURI = URI.createFileURI(dest.getAbsolutePath());

		  // Create a resource for this file.
		  Resource resource = resourceSet.createResource(fileURI);

		  // Add the book and writer objects to the contents.
		  resource.getContents().add(fsa);
		  

		  // Save the contents of the resource to the file system.
		  try
		  {
		    resource.save(Collections.EMPTY_MAP);
		  }
		  catch (IOException e) {}
	}
	
	
}
