#include <stdlib.h>
#include <stdio.h>

int main(int argc, char** argv){
	int *ptr;
	int *end;
	int arr[10];
	
	int i;
	int last = 11 <= argc ? 10 : argc-1;
	for ( i = 0; i < last; i++ ){
		arr[i]=atoi(argv[i+1]);
	} 	
		
	end = arr+10;
	ptr = arr;

	int val;
	do {
		val = *ptr;
		printf("%d\n",val);		
		ptr++;
	} while( val != -1 );

	return 0;
}
