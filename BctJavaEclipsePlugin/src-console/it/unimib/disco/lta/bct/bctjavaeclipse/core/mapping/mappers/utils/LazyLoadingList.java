package it.unimib.disco.lta.bct.bctjavaeclipse.core.mapping.mappers.utils;


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LazyLoadingList<E extends Object> implements List<E> {
	
	private LazyListElementsLoader<E> loader;
	private LinkedList<E> elements = new LinkedList<E>();
	private boolean allLoaded = false;
	
	private class LazyLoadingListIterator implements ListIterator<E> {

		private int position = -1;
		
		public LazyLoadingListIterator(){
		}
		
		public LazyLoadingListIterator(int index){
			if ( index < 0 ){
				throw new IndexOutOfBoundsException();
			}
			position = index -1;
		}
		
		public boolean hasNext() {
			if ( position + 1 < elements.size() ){
				return true;
			}
			try {
				return loader.hasNext();
			} catch (LazyListElementsLoaderException e) {
				return false;
			}
		}

		public E next() {
			if ( ! hasNext() ){
				throw new NoSuchElementException();
			}
			
			++position;
			if ( position < elements.size() ){
				return elements.get(position);
			}
			return get(position);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public void add(E e) {
			throw new UnsupportedOperationException();
		}

		public boolean hasPrevious() {
			return position > 0;
		}

		public int nextIndex() {
			if ( hasNext() ){
				return position+1; 
			}
			return position;
		}

		public E previous() {
			if ( ! hasPrevious() ){
				throw new NoSuchElementException();
			}
			position--;
			return get(position);
		}

		public int previousIndex() {
			if ( position > 0 ){
				return position-1;
			}
			return 0;
		}

		public void set(E e) {
			throw new UnsupportedOperationException();
		}

	}
	
	public LazyLoadingList( LazyListElementsLoader<E> loader ){
		this.loader = loader;
	}


	private void loadAll() {
		if ( allLoaded  ){
			return;
		}
		
		try {
			while ( loader.hasNext() ){
				loadNext();
			}
		} catch (LazyListElementsLoaderException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Load the next element
	 * 
	 */
	private void loadNext() {
		try {
			elements.add(loader.next());
			if ( ! loader.hasNext() ){
				allLoaded = true;
			}
		} catch (LazyListElementsLoaderException e) {
			throw new RuntimeException(e);
		}
	}


	public void clear() {
		
	}

	public E get(int index) {
		if ( index >= loader.getLoadedElementsCount() ){
			loadNext(index-loader.getLoadedElementsCount()+1);
		}
		return elements.get(index);
	}

	/**
	 * Load the next N elements
	 * 
	 * @param elementsToLoad
	 */
	private void loadNext(int elementsToLoad) {
		for ( int i = 0; i < elementsToLoad; ++i ){
			loadNext();
		}
	}


	public int indexOf(Object o) {
		loadAll();
		return elements.indexOf(o);
	}

	public boolean isEmpty() {
		loadAll();
		return elements.isEmpty();
	}

	public Iterator<E> iterator() {
		return new LazyLoadingListIterator();
	}

	public int lastIndexOf(Object o) {
		loadAll();
		return elements.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return new LazyLoadingListIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return new LazyLoadingListIterator(index);
	}

	public boolean remove(Object o) {
		loadAll();
		return elements.remove(o);
	}

	public E remove(int index) {
		loadAll();
		return elements.remove(index);
	}

	public boolean removeAll(Collection<?> c) {
		loadAll();
		return elements.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		loadAll();
		return elements.retainAll(c);
	}

	public E set(int index, E element) {
		loadAll();
		return elements.set(index, element);
	}

	public int size() {
		if ( allLoaded() ){
			return elements.size();	
		}
		try {
			return loader.getTotalElementsCount();
		} catch (LazyListElementsLoaderException e) {
			throw new RuntimeException("Problem while getting the total number of elements in the list.",e);
		}
		
	}

	private boolean allLoaded() {
		return allLoaded;
	}


	public List<E> subList(int fromIndex, int toIndex) {
		loadAll();
		return elements.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		loadAll();
		return elements.toArray();
	}

	public <T> T[] toArray(T[] a) {
		loadAll();
		return elements.toArray(a);
	}

	public void add(int index, E element) {
		loadAll();
		elements.add(index, element);
	}

	public boolean addAll(Collection<? extends E> c) {
		loadAll();
		return elements.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		loadAll();
		return elements.addAll(index, c);
	}

	public void addFirst(E e) {
		loadAll();
		elements.addFirst(e);
	}

	public void addLast(E e) {
		loadAll();
		elements.addLast(e);
	}

	public boolean contains(Object o) {
		loadAll();
		return elements.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		loadAll();
		return elements.containsAll(c);
	}


	public boolean add(E e) {
		loadAll();
		return elements.add(e);
	}
	
	

}
