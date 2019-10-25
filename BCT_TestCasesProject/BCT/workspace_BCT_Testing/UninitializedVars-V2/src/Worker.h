/**
 * Worker.h
 *
 *  Created on: Nov 2, 2012
 *      Author: fabrizio
 */

#ifndef WORKER_H_
#define WORKER_H_

class Worker {
private:
	int state;

public:
	int calculate(int x, int t);

	Worker(){ };

	virtual ~Worker();


};

#endif
