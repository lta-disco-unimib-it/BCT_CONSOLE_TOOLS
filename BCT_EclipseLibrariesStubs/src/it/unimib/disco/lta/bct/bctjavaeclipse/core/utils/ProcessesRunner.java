package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

import java.io.IOException;
import java.util.ArrayList;

import util.JavaRunner;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.MonitoringConfiguration;

public class ProcessesRunner {

	public static void runProcess(MonitoringConfiguration mc, String string,
			String classToRun) {
		Class clazz;
		try {
			clazz = Class.forName(classToRun);
			try {
				JavaRunner.runMainInClass(clazz, new ArrayList<String>(), 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
