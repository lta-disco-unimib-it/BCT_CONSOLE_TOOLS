//to cover all assertions it is necessary to run "goto-instrument --nonde-static <a.out> <a.instr.goto>"

struct str {
	int x;
	char s[2];
};

struct n_str {
	struct str str;
};


int global_x;
struct str global_s;
struct str* global_sp;

int func(int x, struct str s, struct str* sp, struct n_str nested ){
	int y;
	
	//all the assertions are written in a way that they fail if everythig is ok
	// so we will have:
	// assert ( <expressionExpectedToBeFalse )
	// assert ( ! <expressionExpectedToBeTrue )
	

	//following are expected to fail
	assert( s.s[0] != '\0' );	
	assert( s.s[1] != '\0' );	
	assert( s.s[0] == '\0' );	
	assert( s.s[1] == '\0' );	
	
	assert( global_s.s[0] != '\0' );	
	assert( global_s.s[1] != '\0' );	
	assert( global_s.s[0] == '\0' );	
	assert( global_s.s[1] == '\0' );	
	
	assert( global_sp != 0 );	
	assert( global_sp == 0 );	
	assert( global_sp->s[0] != '\0' );	
	assert( global_sp->s[1] != '\0' );	
	assert( global_sp->s[0] == '\0' );	
	assert( global_sp->s[1] == '\0' );	

	//nonstatic fields are always ok
	assert ( x > 0 );
	assert ( x < 0 );
	assert ( x == 0 );
	assert ( y > 0 );
	assert ( y < 0 );
	assert ( y == 0 );
	assert ( s.x > 0 );
	assert ( s.x < 0 );
	assert ( s.x == 0 );
	assert ( sp == 0 );
	assert ( sp != 0 );
	assert ( sp->x > 0 );
	assert ( sp->x < 0 );
	assert ( sp->x == 0 );
		


	//without --nondet-static global_x is == 0
	assert ( global_x > 0 );
	assert ( global_x < 0 );
	assert ( global_x == 0 );
		
	
	
	//without --nondet-static all the fields of global_s are == 0
	assert ( global_s.x > 0 );
	assert ( global_s.x < 0 );
	assert ( global_s.x == 0 );
		
	
	//without --nondet-static, something strange happens:
	//global_sp is considered to be 0
	//global_sp->x is considered to have any value (it is ok because it dereference an unclean pointer)
	assert ( global_sp != 0 );
	assert ( global_sp == 0 );
	assert ( global_sp->x > 0 );
	assert ( global_sp->x < 0 );
	assert ( global_sp->x == 0 );
		

	assert ( nested.str.x == 0 );
	assert ( nested.str.x != 0 );
}

int cbmc_r();
struct str cbmc_s();
void * cbmc_p();

int main(){
	func( cbmc_r(), *(struct str*)cbmc_p(), cbmc_r(), *(struct n_str*)cbmc_p() );

}
