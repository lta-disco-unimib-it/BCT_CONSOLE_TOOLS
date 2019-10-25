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
		cout << "Test-1" << endl;
		Learner l;
		Data d;
		d.setData(5);
		l+d;

		cout << l.getValueLearned() << endl;
	}

	{
		cout << "Test-2" << endl;
		Learner l;
		Data d;

		l+d;

		cout << l.getValueLearned() << endl;
	}

	{
		cout << "Test-3" << endl;
		Learner l;
		Data d;
		d.setData(5);
		l+d;

		cout << l.getValueLearned() << endl;

		l+d;

		cout << l.getValueLearned() << endl;
	}

}


