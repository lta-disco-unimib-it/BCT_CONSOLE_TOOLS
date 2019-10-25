/*
 * Data.cpp
 *
 *  Created on: May 20, 2012
 *      Author: fabrizio
 */

#include "Data.h"

Data::Data() {
	// TODO Auto-generated constructor stub
	empty = true;
}

Data::Data(int d) {
	// TODO Auto-generated constructor stub
	empty = false;
	data = d;
}

Data::~Data() {
	// TODO Auto-generated destructor stub
}

bool Data::isEmpty(){
	return empty;
}

int Data::getData(){
	if ( isEmpty() ){
		throw new EmptyException();
	}
	return data;
}
