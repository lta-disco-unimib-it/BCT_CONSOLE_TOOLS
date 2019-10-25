#include "Worker.h"
#include <stdio.h>

int parentVal = 3;
int childVal = 2;


int main(int argc,char* argv[]){

	Worker w;
	w.calculate(parentVal,0);

	return 0;
}
