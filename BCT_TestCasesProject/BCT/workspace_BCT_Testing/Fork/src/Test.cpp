/**
 * Test.cpp
 *
 *  Created on: Nov 2, 2012
 *      Author: fabrizio
 */
#include "Worker.h"
#include <unistd.h>
#include <stdio.h>

int parentVal = 1;
int childVal = 2;


int main(int argc,char* argv[]){

	Worker w;
	int pid = fork();

	if ( pid == 0 ){
		w.calculate(parentVal,1);
	} else {
		w.calculate(childVal,2);
	}

	return 0;
}
