/*
 * Data.h
 *
 *  Created on: May 20, 2012
 *      Author: fabrizio
 */

#ifndef DATA_H_
#define DATA_H_

#include "EmptyException.h"

class Data {

	bool empty;
	int data;

public:
	Data();
	virtual ~Data();

	bool isEmpty();

	int getData();

	void setData( int value );
};

#endif /* DATA_H_ */
