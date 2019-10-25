#include <stdio.h>

int id=-1;
int res;

void new_id(){
	int x = 0;
	++id;
}

int main(){
	int i;
	for ( i = 0; i < 9; i++){
		new_id();
		printf("%d\n",id);
	}
	return 0;
}
