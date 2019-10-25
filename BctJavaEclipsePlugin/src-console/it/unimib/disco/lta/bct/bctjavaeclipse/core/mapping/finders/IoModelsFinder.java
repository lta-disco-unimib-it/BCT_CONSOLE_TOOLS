package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.finders;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities.IoModel;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.IoExpression;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.MapperException;

import java.util.List;

public interface IoModelsFinder {

	public abstract List<IoModel> getIoModels() throws MapperException;

	public abstract IoModel getIoModel(Method m) throws MapperException;

	public abstract List<IoExpression> getIoExpressionsEnter(Method method)
			throws MapperException;

	public abstract List<IoExpression> getIoExpressionsExit(Method method)
			throws MapperException;

}