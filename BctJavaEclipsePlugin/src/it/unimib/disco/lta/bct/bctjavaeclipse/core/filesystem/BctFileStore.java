package it.unimib.disco.lta.bct.bctjavaeclipse.core.filesystem;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.PluginConstants;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.utils.URIUtil;

import java.io.InputStream;
import java.net.URI;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class BctFileStore extends FileStore {

	URI uri;
	
	public BctFileStore(URI uri) {
		this.uri = uri;
	}

	@Override
	public String[] childNames(int options, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFileInfo fetchInfo(int options, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFileStore getChild(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return URIUtil.parseQueryString(uri.getQuery()).get(PluginConstants.RESOURCE_IDENTIFIER);
	}

	@Override
	public IFileStore getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream openInputStream(int options, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI toURI() {
		return uri;
	}

}
