/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani, and other authors indicated in the source code below.
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
