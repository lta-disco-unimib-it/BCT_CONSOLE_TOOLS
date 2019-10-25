package it.unimib.disco.lta.bct.bctjavaeclipse.core.bctEntities;

import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.Method;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.configuration.ValueHolder;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.CollectionWrappingLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.LoaderException;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders.WrappingLoader;
import it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.file.IoExpression;

import java.util.List;

public class IoModel extends DomainEntity {
	private Method method;
	private ValueHolder<List<IoExpression>> expressionsEnterLoader;
	private ValueHolder<List<IoExpression>> expressionsExitLoader;
	private CollectionWrappingLoader<IoExpression> expressionsEnterWrapper;
	private CollectionWrappingLoader<IoExpression> expressionsExitWrapper;
	
	public IoModel(Method method, ValueHolder<List<IoExpression>> enterLoader, ValueHolder<List<IoExpression>> exitLoader) {
		this(null, method, enterLoader, exitLoader);
	}
	
	public IoModel(String id, Method method, ValueHolder<List<IoExpression>> enterLoader, ValueHolder<List<IoExpression>> exitLoader) {
		super(id);
		this.method = method;
		this.expressionsEnterLoader = enterLoader;
		this.expressionsExitLoader = exitLoader;
	}

	public Method getMethod() {
		return method;
	}
	
	public List<IoExpression> getExpressionsEnter() throws LoaderException{
		return expressionsEnterLoader.getValue();
	}
	
	public List<IoExpression> getExpressionsExit() throws LoaderException{
		return expressionsExitLoader.getValue();
	}

	public void setExpressionsEnter(List<IoExpression> ioExpressions) {
		expressionsEnterLoader = new ValueHolder<List<IoExpression>>(new CollectionWrappingLoader<IoExpression>(ioExpressions) );
	}
	
	private void enableChanges() throws LoaderException {
		if ( expressionsEnterWrapper == null ){
			expressionsEnterWrapper = new CollectionWrappingLoader<IoExpression>(expressionsEnterLoader.getValue());
			expressionsExitWrapper = new CollectionWrappingLoader<IoExpression>(expressionsExitLoader.getValue());
		}
	}

	public void setExpressionsExit(List<IoExpression> ioExpressions) {
		expressionsExitLoader = new ValueHolder<List<IoExpression>>(new CollectionWrappingLoader<IoExpression>(ioExpressions) );
	}
	
	public boolean removeExpressionEnter( IoExpression expression ) throws LoaderException{
		enableChanges();
		return expressionsEnterWrapper.removeItem(expression);
	}
	
	public boolean removeExpressionExit( IoExpression expression ) throws LoaderException{
		enableChanges();
		return expressionsExitWrapper.removeItem(expression);
	}
	
	public boolean containsExpressionEnter( IoExpression expression ) throws LoaderException{
		enableChanges();
		return expressionsEnterWrapper.containsItem(expression);
	}
	
	public boolean containsExpressionExit( IoExpression expression ) throws LoaderException{
		enableChanges();
		return expressionsExitWrapper.containsItem(expression);
	}
}
