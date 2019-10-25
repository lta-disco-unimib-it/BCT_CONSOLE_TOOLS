package org.eclipse.core.runtime;

import cpp.gdb.EnvUtil;

public class Platform {

	public static IExtensionRegistry getExtensionRegistry(){
		return new ConsoleExtensionRegistry();
	}

	public static String getOS() {
		return System.getProperty("os.arch");
	}
}
