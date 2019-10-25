/*
 * StoreHandler.h
 *
 *  Created on: Nov 19, 2012
 *      Author: fabrizio
 */

#ifndef STOREHANDLER_H_
#define STOREHANDLER_H_

#include <string>
#include <map>
#include <list>
#include <utility>
#include "Store.h"

using namespace std;

class StoreHandler {

public:
	StoreHandler();
	virtual ~StoreHandler();

	list< pair<string,int> >* availableItems( Store& store, list<string> chainItems );
};

#endif /* STOREHANDLER_H_ */
