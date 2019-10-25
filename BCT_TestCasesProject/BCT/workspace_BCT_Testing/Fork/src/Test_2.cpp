/**
 * Test.cpp
 *
 *  Created on: Nov 2, 2012
 *      Author: fabrizio
 */
#include "Worker.h"
#include <unistd.h>
#include <stdio.h>

int main(int argc,char* argv[]){

	Worker w;
	int pid = fork();

	if ( pid == 0 ){
		w.calculate(5,1);
	} else {
		w.calculate(5,2);
	}

	return 0;
}
