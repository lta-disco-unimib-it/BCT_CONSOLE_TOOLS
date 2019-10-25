package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.IoModelsFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.IoExpression;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

public class IoExpressionEnterLoader extends IoExpressionLoader {

	
	public IoExpressionEnterLoader(Method method, IoModelsFinder finder) {
		super(method,finder);
	}


	public List<IoExpression> load() throws LoaderException {
		Method method = null;
		try {
			method = getMethod();
			return finder.getIoExpressionsEnter(method);
		} catch (MapperException e) {
			throw new LoaderException("Cannot load values for model: "+method.getSignature(),e);
		}
	}

}
