package it.unimib.disco.lta.bct.bctjavaeclipse.core.filesystem;

import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileSystem;

public class BctFileSystem extends FileSystem {

	public BctFileSystem() {}

	@Override
	public IFileStore getStore(URI uri) {
		return new BctFileStore(uri);
	}

}
