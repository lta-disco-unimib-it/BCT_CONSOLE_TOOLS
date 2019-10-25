/*
 * Learner.h
 *
 *  Created on: May 20, 2012
 *      Author: fabrizio
 */

#ifndef LEARNER_H_
#define LEARNER_H_

#include "Data.h"

class Learner {

	int val;

public:
	Learner();
	virtual ~Learner();

	void operator+(Data &data){
		if( data.isEmpty() )
			return;

		val += data.getData();
	}

	int getValueLearned(){
		return val;
	}

	static int getID(){ return 7; };
};

#endif /* LEARNER_H_ */
