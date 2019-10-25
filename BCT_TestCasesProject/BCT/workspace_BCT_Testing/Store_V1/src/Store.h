/*
 * STORE_H_.h
 *
 *  Created on: Nov 25, 2011
 *      Author: usiusi
 */

#ifndef STORE_H_
#define STORE_H_

#include <string>
#include <map>
#include <list>

using namespace std;

class Store {

	map<std::string, int> items;

public:
	Store();
	virtual ~Store();

	/**
	 * Returns the number of available items.
	 * Returns NULL if the person is not a worker.
	 *
	 */
	int availableQty( string productId );

	/**
	 * Returns true if the person is a worker
	 *
	 */
	bool inCatalogue( string productId );

	/**
	 * Add an item
	 */
	void addItem( string itemId, int qty );
};

#endif /* STORE_H_ */
