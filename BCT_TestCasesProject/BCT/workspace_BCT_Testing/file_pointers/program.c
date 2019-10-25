#include <stdio.h>
#include <stdlib.h>

void process(FILE *fp){
	int n;
	fscanf(fp,"%d",&n);
	printf("VALUE=%d\n",n);
}

int main(int argc,char **argv){

	FILE *fp;

	fp = fopen("test.txt","r");

	process(fp);

	return 0;
}
