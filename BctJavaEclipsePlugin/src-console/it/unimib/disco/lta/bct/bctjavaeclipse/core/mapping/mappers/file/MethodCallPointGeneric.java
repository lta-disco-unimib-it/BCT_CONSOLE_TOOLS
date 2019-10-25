package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.MethodCallPoint;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.ProgramPointRawLoader;

public class MethodCallPointGeneric extends MethodCallPoint {

	public MethodCallPointGeneric(Method method, String methodCallId,
			ProgramPointRawLoader programPointRawLoader) {
		super(method, methodCallId, programPointRawLoader, Types.GENERIC);
		// TODO Auto-generated constructor stub
	}

	public int getLineNumber(){
		String point = getMethod().getSignature().trim();
		int pos = point.lastIndexOf(':');
		if ( pos == -1 ){
			return -1;
		}

		String intNumber = point.substring(pos+1);

		try {
			return Integer.valueOf(intNumber);
		} catch ( NumberFormatException e ){
			e.printStackTrace();
			return -1;
		}
	}

}
