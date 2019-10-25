/**
 * Worker.cpp
 *
 *  Created on: Nov 2, 2012
 *      Author: fabrizio
 */

#include "Worker.h"

int myGlobal = -1;

Worker::~Worker() {
	// TODO Auto-generated destructor stub
}

int Worker::calculate(int x, int t){
	int y = 0;
	int j = y;

	int res = j + x + t + state + myGlobal;

	return res;
}
