#include <stdio.h>

int id;

static int
get_id(){
	int x = 0;
	int res = id++;
	if ( res == 0 )
		return 0;
	return res;
}

int main(){
	int i;
	for ( i = 0; i < 9; i++){
		int my_id = get_id();
		printf("%d\n",my_id);
	}
	return 0;
}
