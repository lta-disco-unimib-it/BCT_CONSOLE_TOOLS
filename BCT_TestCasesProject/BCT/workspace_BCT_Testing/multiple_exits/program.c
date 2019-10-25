#include <stdio.h>

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

	printf("%d\n",n);	

	return 0;
}
