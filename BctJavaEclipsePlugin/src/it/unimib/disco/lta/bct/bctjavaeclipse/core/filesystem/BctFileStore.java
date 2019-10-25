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
