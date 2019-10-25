#include "Worker.h"

int myGlobal;

Worker::~Worker() {
	// TODO Auto-generated destructor stub
}

int Worker::calculate(int x, int t){
	int y;
	int j = y;

	int res = j + x + t + state + myGlobal;

	return res;
}

