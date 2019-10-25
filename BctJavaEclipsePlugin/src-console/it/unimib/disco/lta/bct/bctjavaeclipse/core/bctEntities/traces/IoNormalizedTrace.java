package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointNormalized;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;

import java.util.List;

public class IoNormalizedTrace extends NormalizedTrace {

	private ValueHolder<List<ProgramPointNormalized>> programPoints;
	private Method method;
	
	public IoNormalizedTrace(String id, Method method) {
		super(id);
		this.method = method;
	}

	public void setProgramPoints( ValueHolder<List<ProgramPointNormalized>> valueHolder ){
		this.programPoints = valueHolder;
	}
	
	public List<ProgramPointNormalized> getProgramPoints() throws LoaderException {
		return programPoints.getValue();
	}
	
	public Method getMethod() {
		return method;
	}

}
