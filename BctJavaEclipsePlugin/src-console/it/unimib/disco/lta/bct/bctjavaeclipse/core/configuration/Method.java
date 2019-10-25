package it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration;

import util.componentsDeclaration.JavaSignatureParser;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.FSAModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.InteractionNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoNormalizedTrace;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.traces.IoRawTrace;

public class Method {
	private String signature;
	
	
	public Method ( String signature ){
		this.signature = signature;
	}

	public String getSignature() {
		return signature;
	}

	public static <T> Method getMethod(T bctResource) {
		Method method = null;
		if (bctResource instanceof IoModel) {
			IoModel ioModel = (IoModel) bctResource;
			method = ioModel.getMethod();
		} else if (bctResource instanceof FSAModel) {
			FSAModel fsaModel = (FSAModel) bctResource;
			method = fsaModel.getMethod();
		} else if (bctResource instanceof IoRawTrace) {
			IoRawTrace rawTrace = (IoRawTrace) bctResource;
			method = rawTrace.getMethod();
		} else if (bctResource instanceof IoNormalizedTrace) {
			IoNormalizedTrace normalizedTrace = (IoNormalizedTrace) bctResource;
			method = normalizedTrace.getMethod();
		}  else if (bctResource instanceof InteractionNormalizedTrace) {
			InteractionNormalizedTrace normalizedTrace = (InteractionNormalizedTrace) bctResource;
			method = normalizedTrace.getMethod();
		}
		return method;
	}

	public String getOwnerClassName() {
		return JavaSignatureParser.getCanonicalClassNameFromCompleteMethodSignature(signature);
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null ){
			return false;
		}
		if ( ! ( obj instanceof Method ) ){
			return false;
		}
		
		return signature.equals( ( (Method)obj ).signature);
	}

	@Override
	public int hashCode() {
		return signature.hashCode();
	}

	@Override
	public String toString() {
		return "[Method: "+signature+"]";
	}
}
