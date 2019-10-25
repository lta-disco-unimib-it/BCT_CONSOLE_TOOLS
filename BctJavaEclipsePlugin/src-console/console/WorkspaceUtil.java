package console;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;


public class WorkspaceUtil {
	
	public static void setWorkspaceRoot( File workspaceRoot ) {
		
		try {
			Method method;
			method = ResourcesPlugin.class.getMethod("setWorkspace", File.class);
			method.invoke(null, workspaceRoot);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static File getWorkspaceRootFile(  ) {
		
		return ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();
	}

	

}
