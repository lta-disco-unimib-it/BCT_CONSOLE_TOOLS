package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.ProgramPointRaw;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;

import java.util.List;

/**
 * This is an IO raw trace.
 *  
 * @author Fabrizio Pastore - fabrizio.pastore@disco.unimib.it
 *
 */
public class IoRawTrace extends RawTrace {

	private ValueHolder<List<ProgramPointRaw>> programPoints;
	private Method method;
	
	public IoRawTrace(String id, Method method) {
		super(id);
		this.method = method;
	}

	public void setProgramPoints( ValueHolder<List<ProgramPointRaw>> valueHolder ){
		this.programPoints = valueHolder;
	}
	
	public IoRawTrace(String id, Method method, ValueHolder<List<ProgramPointRaw>> programPoints) {
		this(id,method);
		this.programPoints = programPoints;
	}

	public List<ProgramPointRaw> getProgramPoints() throws LoaderException {
		return programPoints.getValue();
	}
	
	public Method getMethod() {
		return method;
	}

}
