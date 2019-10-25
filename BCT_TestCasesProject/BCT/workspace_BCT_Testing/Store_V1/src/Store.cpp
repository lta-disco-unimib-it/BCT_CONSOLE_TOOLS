/*
 * WorkersMap.cpp
 *
 *  Created on: Nov 25, 2011
 *      Author: usiusi
 */

#include "Store.h"

Store::Store() {
	// TODO Auto-generated constructor stub

}

Store::~Store() {
	// TODO Auto-generated destructor stub
}


int Store::availableQty( string productId ){

	if ( ! inCatalogue( productId ) ){
		return 0;
	}

	return items.find( productId )->second;

}


void Store::addItem( string itemId, int qty ){
	items.insert( pair<string,int>(itemId, qty) );
}

bool Store::inCatalogue( string productId ){
	if ( items.count( productId ) == 0 ){
		return false;
	}
	return true;
}




