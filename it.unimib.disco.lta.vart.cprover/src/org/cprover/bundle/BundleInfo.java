/*
 * Created on May 11, 2005
 *
 * @author Fabio Zadrozny
 */
package org.cprover.bundle;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.osgi.framework.Bundle;

/**
 * @author Fabio Zadrozny
 */
public class BundleInfo implements IBundleInfo{

    private Bundle bundle;

    public BundleInfo(Bundle bundle) {
        this.bundle = bundle;
    }

    /**
     * @throws CoreException
     * @see org.cprover.bundle.IBundleInfo#getRelativePath(org.eclipse.core.runtime.IPath)
     */
    public File getRelativePath(IPath relative) throws CoreException {
        return BundleUtils.getRelative(relative, bundle);
    }
    
    /**
     * @see org.cprover.bundle.IBundleInfo#getPluginID()
     */
    public String getPluginID() {
        return bundle.getSymbolicName();
    }

    
    private ImageCache imageCache;
    
    /**
     * @see org.cprover.bundle.IBundleInfo#getImageCache()
     */
    public ImageCache getImageCache() {
        if(imageCache == null){
            imageCache = new ImageCache(bundle.getEntry("/"));
        }
        return imageCache;
    }
    
    

}
