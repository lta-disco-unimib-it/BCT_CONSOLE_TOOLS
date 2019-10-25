#include <malloc.h>
#include <stdio.h>

int calcAvailableQty(int id){
	if ( id > 100 || id < 0 ){
		return 0;
	}
	return id;
}

int* availableItems(int size, int ids[]){
	int i;
	int *res;
	int total = 0;
	int c=-1;
	for( i=0; i<size;i++){
		if ( total == 0 ){
			res = malloc ( sizeof ( int ) * size );	
		}
		int availableQty=calcAvailableQty(ids[i]);
		if ( availableQty == 0 )
			continue;
		c++;
		res[c]=availableQty;
		total+=availableQty;
	}
	while((c+1)<size){
		c++;
		res[c]=-1;
	}
	return res;
}

void main(int argc,char** argv){
	int ids[argc-1];
	
	int c;
	for( c = 1; c< argc; c++ ){
		ids[c-1]=atoi(argv[c]);
	}

	int * res = availableItems(argc-1,ids);
	for ( c =1; c < argc; c++){
		printf("%d\n",res[c-1]);
	}
		
}
