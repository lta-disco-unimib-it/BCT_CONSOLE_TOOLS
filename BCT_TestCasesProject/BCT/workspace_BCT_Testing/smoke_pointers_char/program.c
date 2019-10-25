#include <stdlib.h>
#include <stdio.h>

int main(int argc, char** argv){
	char *ptr;
	char *end;
	char arr[10];
	
	int i;
	int last = 11 <= argc ? 10 : argc-1;
	for ( i = 0; i < last; i++ ){
		arr[i]=argv[i+1][0];
	} 	
		
	end = arr+10;
	ptr = arr;

	int val;
	do {
		val = *ptr;
		printf("%d\n",val);		
		ptr++;
	} while( val != '-' );

	return 0;
}
