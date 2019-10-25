#include <stdlib.h>


long long calculateLong( int x, long long inp ){
	long long res = inp + 2;
	res += 1;
	res += 1;
	return res;
}

int main(){
	long long f = calculateLong( 15, +2 );
	f += 1;

	return 0;
}
