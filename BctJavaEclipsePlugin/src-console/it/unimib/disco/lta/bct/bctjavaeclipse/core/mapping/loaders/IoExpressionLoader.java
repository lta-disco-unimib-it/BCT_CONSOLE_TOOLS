package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders.IoModelsFinder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.IoExpression;

import java.util.List;

public abstract class IoExpressionLoader extends ModelsLoader<List<IoExpression>> {

	protected IoModelsFinder finder;

	public IoExpressionLoader(Method method, IoModelsFinder finder) {
		super(method);
		this.finder = finder;
	}


}
