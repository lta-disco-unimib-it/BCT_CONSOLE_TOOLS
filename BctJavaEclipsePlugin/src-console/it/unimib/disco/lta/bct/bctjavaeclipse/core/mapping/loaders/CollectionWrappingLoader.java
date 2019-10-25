package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.loaders;

import java.util.List;

public class CollectionWrappingLoader<T> extends WrappingLoader<List<T>> {

	public CollectionWrappingLoader(List<T> data) {
		super(data);
	}

	public boolean removeItem(T itemToRemove){
		return ((List<T>)data).remove(itemToRemove);
	}
	
	public boolean containsItem(T itemToRemove){
		return ((List<T>)data).contains(itemToRemove);
	}
}
