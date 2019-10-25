package org.eclipse.jdt.core;

import org.eclipse.core.runtime.IPath;

public interface IPackageFragmentRoot {

	int K_SOURCE = 0;

	public String getElementName();

	public int getKind();

	public IPath getPath();

}
