/*
 * StoreHandler.cpp
 *
 *  Created on: Nov 19, 2012
 *      Author: fabrizio
 */

#include "StoreHandler.h"

StoreHandler::StoreHandler() {
	// TODO Auto-generated constructor stub

}

StoreHandler::~StoreHandler() {
	// TODO Auto-generated destructor stub
}

bool __comparator(pair<string,int>& l,pair<string,int>& r){
	return l.first < r.first;
}

list< pair<string,int> >* StoreHandler::availableItems( Store& store, list<string> products ){
	list< pair<string,int> >* storeItems = NULL;
	long total = 0;

	for(list<string>::iterator i=products.begin(); i != products.end(); ++i){
		if ( total == 0 ){//first item create the list
			storeItems = new list< pair<string,int> >();
		}
		int items = store.availableQty( *i );
		if ( items == 0 ) continue;
		storeItems->push_back(make_pair(*i,items));
		total += items;
	}
	if ( storeItems != NULL ){
		storeItems->sort(__comparator);
		storeItems->push_back(make_pair("Total",total));
	}
	return storeItems;
}
