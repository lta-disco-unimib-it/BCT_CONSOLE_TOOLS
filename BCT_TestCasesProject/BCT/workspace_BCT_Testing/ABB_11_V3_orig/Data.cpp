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
	data = -1;
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
	return data;
}
