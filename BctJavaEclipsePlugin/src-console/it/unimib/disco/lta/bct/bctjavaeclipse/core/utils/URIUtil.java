package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.IO_RAW_TRACES_PATH;
import static it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants.INTERACTION_RAW_TRACES_PATH;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctIntegrationLayer.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

public class URIUtil {
	
	/**
	 * @param relativePath workspace relative path.
	 * @return The URI represents location indicated by <code>relativePath</code>.
	 */
	public static URI buildURI(String relativePath) {
		
		IFile correspondingFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(relativePath));
		if ( correspondingFile.exists() ){
			return correspondingFile.getLocationURI(); //this way we handle links
		}
		
		String workspaceURI = ResourcesPlugin.getWorkspace().getRoot().getLocationURI().toString();
		String uriString = workspaceURI + relativePath;
		try {
			URI uri = new URI(uriString);
			
			
			
			return uri;
		} catch (URISyntaxException e) {
			Logger.getInstance().log(e);
			return null;
		}
	}
	
	public static Hashtable<String, String> parseQueryString(String query) { 
		Hashtable<String, String> values = new Hashtable<String, String>();
		
		String[] elements = query.split("&"); // element is "id=value" 
		for (String element : elements) {
			String[] pair = element.split("="); 
			values.put(pair[0], pair[1]); 
		} 
		return values; 
	 }
	
	public static boolean rawTraceURI(URI uri) {
		String path = uri.getPath();
		if (path.equals(IO_RAW_TRACES_PATH) || path.equals(INTERACTION_RAW_TRACES_PATH))
			return true;
		return false;
	}

	/**
	 * Returns true if the passed URI is a BCT uri
	 * 
	 * e.g. bct:/DataRecording/...
	 * 
	 * @param uri
	 * @return
	 */
	public static boolean isBCT(URI uri) {
		if ( uri == null ){
			return false;
		}
		if ( uri.getScheme().toLowerCase().equals("bct") ){
			return true;
		}
		return false;
	}
}
