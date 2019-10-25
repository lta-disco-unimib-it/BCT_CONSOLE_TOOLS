
#include "store.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "map_lib.h"

int allTestCases;
int passingTestCases;
int failingTestCases;

int currentFail;

void assertEquals ( long v1, long v2  ){
	if ( currentFail ){
		return;
	}

	if ( v1 != v2 ){
		printf(" found %ld, expected %ld \n", v2, v1);
		currentFail = 1;
	}

}


void testNoUnits(){
	t_user* user = create_user();


	int items = available_items( user);

	assertEquals( 0, items );
}

void testAvailableEmptyUnit(){
	t_map_t * user = map_create();
	assertEquals( 0, isAvailable(user) );
}

void testAvailableOneUnit(){
	t_map_t * user = map_create();
	user->name = "MacBook";
	user->value = 1;
	assertEquals( 1, isAvailable(user) );
}

void testAvailableMultipleUnits(){
	t_map_t * user = map_create();
	user->name = "MacBook";
	user->value = 5;
	assertEquals( 1, isAvailable(user) );
}

void testOutOfStockUnit(){
	t_map_t * user = map_create();
	user->name = "MacBook";
	user->value = 0;
	assertEquals( 0, isAvailable(user) );
}




void testOneUnit(){
	t_user* user = create_user();


	add_unit( user, "MacBook", 5 );

	int items = available_items(  user);

	assertEquals( 1, items );
}

void testMultipleUnits(){
	t_user* user = create_user();

	add_unit( user, "MacBook", 5 );

	assertEquals( 5, get_unit(user,"MacBook") );
	assertEquals( 0, get_unit(user,"Dell 17") );

	add_unit( user, "Dell 17", 2 );

	assertEquals( 2, get_unit(user,"Dell 17") );

	add_unit( user, "Asus 15", 4 );

	assertEquals( 4, get_unit(user,"Asus 15") );

	int items = available_items( user);

	assertEquals( 3, items );
}

void testMultipleUnitsIncremental(){
	t_user* user = create_user();

	assertEquals( 0, available_items( user) );

	add_unit( user, "MacBook", 7 );

	assertEquals( 1, available_items( user) );

	add_unit( user, "MakBookPro", 1 );

	assertEquals( 2, available_items( user) );

	add_unit( user, "Dell 17", 4 );

	assertEquals( 3, available_items( user) );

	add_unit( user, "Acer Aspire 15", 9 );

	assertEquals( 4, available_items( user) );

	add_unit( user, "Acer Aspire 17", 0 );

	assertEquals( 4, available_items( user) );

	add_unit( user, "Dell 15", 2 );

	assertEquals( 5, available_items( user) );

	add_unit( user, "Dell 21", 6 );

	assertEquals( 6, available_items( user) );

}





void runTest( char* testCaseName, void (*testCase)() ){
	allTestCases++;

	currentFail = 0;
	testCase();
	if ( currentFail == 0 ){
		passingTestCases++;
		printf( "Test passed: %s \n", testCaseName);
	} else {
		failingTestCases++;
		printf( "Test failed: %s \n", testCaseName);
	}
}



void run( char *testToRun ){

	if ( testToRun == NULL || strcmp(testToRun, "testAvailableOneUnit") == 0 ){
		runTest ( "testAvailableOneUnit" , &testAvailableOneUnit );
	}

	if ( testToRun == NULL || strcmp(testToRun, "testAvailableEmptyUnit") == 0 ){
			runTest ( "testAvailableEmptyUnit" , &testAvailableEmptyUnit );
	}

	if ( testToRun == NULL || strcmp(testToRun, "testAvailableMultipleUnits") == 0 ){
		runTest ( "testAvailableMultipleUnits" , &testAvailableMultipleUnits );
	}

	if ( testToRun == NULL || strcmp(testToRun, "testOutOfStockUnit") == 0 ){
		runTest ( "testOutOfStockUnit" , &testOutOfStockUnit );
	}



	if ( testToRun == NULL || strcmp(testToRun, "testNoUnits") == 0 ){
		runTest ( "testNoUnits" , &testNoUnits );
	}

	if ( testToRun == NULL || strcmp(testToRun, "testOneUnit") == 0 ){
		runTest ( "testOneUnit" , &testOneUnit );
	}

	if ( testToRun == NULL || strcmp(testToRun, "testMultipleUnits") == 0 ){
		runTest ( "testMultipleUnits" , &testMultipleUnits );
	}

	if ( testToRun == NULL || strcmp(testToRun, "testMultipleUnitsIncremental") == 0 ){
		runTest ( "testMultipleUnitsIncremental" , &testMultipleUnitsIncremental );
	}



}

//int nondet_int();

//int main(){
//	available_items(nondet_int(),nondet_int());
//}

int main(int argc,char* argv[]){
	printf ( "Running test cases\n" );

	if ( argc == 1 ){
		run(NULL);
	} else {
		int i;
		for ( i = 1; i < argc; i++){
			run (argv[i]);
		}
	}

	if ( allTestCases == passingTestCases ){
		printf( "\n\nAll test cases were successfully executed\n\n" );
	} else {
		printf(  "\n\nTest terminated with failures\n\n" );
	}

	printf ( "test cases executed: %d \n", allTestCases );

	printf ( "test cases passed: %d \n", passingTestCases );

	printf ( "test cases failed: %d \n", failingTestCases );

	return 0;
}

