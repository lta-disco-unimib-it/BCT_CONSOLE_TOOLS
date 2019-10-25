//to cover all assertions it is necessary to run "goto-instrument --nonde-static <a.out> <a.instr.goto>"

#include <stdio.h>

struct str {
	int x;
	char s[10];
};

int global_x;
struct str global_s;
struct str* global_sp;

int func(int x, struct str s, struct str* sp){
	int i;

	for( i = 0; i < 10; i++ ){
		printf("%c.",s.s[i]);
	}
	printf("\n");
		
	for( i = 0; i < 10; i++ ){
		printf("%c.",sp->s[i]);
	}
	printf("\n");
}

int cbmc_r();
struct str cbmc_s();

int main(){
	struct str p;

	char a[] = "ABC";
	char b[3];
	b[0]='A';
	b[1]='B';
	b[2]='C';

	func(0,p,&p);
	func(0,global_s,&global_s);

	printf( "%d", (a==b) );
}
