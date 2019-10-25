/**
 * Test.cpp
 *
 *  Created on: Nov 2, 2012
 *      Author: fabrizio
 */
#include "Worker.h"
#include <unistd.h>
#include <stdio.h>

int parentVal = 5;
int childVal = 5;


int main(int argc,char* argv[]){

	Worker w;
	int pid = fork();

	if ( pid == 0 ){
		w.calculate(childVal,2);
	} else {
		w.calculate(parentVal,1);
	}

	return 0;
}
