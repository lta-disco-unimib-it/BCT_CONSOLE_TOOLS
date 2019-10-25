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
		Data d(4);

		l+d;

		cout << l.getValueLearned() << endl;
	}
	{
		Learner l;
		Data d;

		l+d;

		cout << l.getValueLearned() << endl;
	}
}


