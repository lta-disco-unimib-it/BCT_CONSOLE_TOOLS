#include <stdio.h>
#include <stdlib.h>

int factorial( int n ) {
	int y;
	if ( n == 0 ){
		return 1;
	}
	y=2;
	return n*factorial(n-1);

}

int main(int argc, char **argv){

	int n;

	n = factorial(5);
	exit(0);
	printf("%d\n",n);	

	return 0;
}
