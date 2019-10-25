package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint.Types;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.ProgramPointRawLoader;

public class MethodCallPointEnter extends MethodCallPoint {

	public MethodCallPointEnter(Method method, String methodCallId, ProgramPointRawLoader programPointRawLoader) {
		super(method,methodCallId, programPointRawLoader, Types.ENTER);
	}


}
