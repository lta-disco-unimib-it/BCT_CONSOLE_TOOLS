/*
 * program.cpp
 *
 *  Created on: May 20, 2012
 *      Author: fabrizio
 */

#include <iostream>
#include "Learner.h"
#include "Data.h"

using namespace std;

int main(){
	{
		Learner l;
		Data d;

		l+d;

		cout << l.getValueLearned() << endl;
	}
	Learner::getID();
	{
		Learner l;
		Data d(0);

		l+d;

		cout << l.getValueLearned() << endl;
	}
	
	Learner *p = new Learner();
	//delete p;

	{
		Learner l;
		Data d(5);

		l+d;

		cout << l.getValueLearned() << endl;
	}

}


