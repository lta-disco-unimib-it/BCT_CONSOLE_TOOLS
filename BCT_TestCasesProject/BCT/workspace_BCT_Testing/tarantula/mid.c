#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[]){
	int x,y,z,m;

	x=atoi(argv[1]);
	y=atoi(argv[2]);
	z=atoi(argv[3]);


 	m = z;
	if (y<z) {
		if(x<y)
			m=y;
		else if (x<z)
			m=y;
	} else {
		if (x>y)
			m=y;
		else if (x>z)
			m = x;
	}
	printf("Middle number is: %d\n",m);
}
