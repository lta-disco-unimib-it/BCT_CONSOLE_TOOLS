package it.unimib.disco.lta.bct.bctjavaeclipse.core.utils;

import java.util.ArrayList;


public class BCTObservaleIncapsulated implements BCTObservable {

	private ArrayList<BCTObserver> observers = new ArrayList<BCTObserver>();
	private BCTObservable owner;
	private static boolean disabled = false;
	
	public static void disableObservers(){
		disabled = true;
	}
	
	public static void enableObservers(){
		disabled = false;
	}

	public BCTObservaleIncapsulated( BCTObservable owner ){
		this.owner = owner;
	}
	
	public void addBCTObserver( BCTObserver bctObserver ){
		observers.add(bctObserver);
	}
	
	public void notifyBCTObservers(Object message){
		if( disabled ){
			return;
		}
		
		for ( BCTObserver observer : observers ){
			observer.bctObservableUpdate(owner,message);
		}
	}
	

}
