
#include "game.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

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

	t_map_t* list;

	int items = available_items( &list, user);

	assertEquals( 0, items );
}


void testOneUnit(){
	t_user* user = create_user();

	t_map_t* list;

	add_unit( user, "Soldiers", 5 );

	int items = available_items( &list, user);

	assertEquals( 1, items );
}

void testMultipleUnits(){
	t_user* user = create_user();

	t_map_t* list;

	add_unit( user, "Soldiers", 5 );

	assertEquals( 5, get_unit(user,"Soldiers") );
	assertEquals( 0, get_unit(user,"Tank") );

	add_unit( user, "Tank", 2 );

	assertEquals( 2, get_unit(user,"Tank") );

	add_unit( user, "Cavalry Tank", 4 );

	assertEquals( 4, get_unit(user,"Cavalry Tank") );

	int items = available_items( &list, user);

	assertEquals( 3, items );
}

void testMultipleUnitsIncremental(){
	t_user* user = create_user();

	t_map_t* list;


	assertEquals( 0, available_items( &list, user) );

	add_unit( user, "Soldiers", 7 );

	assertEquals( 1, available_items( &list, user) );

	add_unit( user, "Tank", 1 );

	assertEquals( 2, available_items( &list, user) );

	add_unit( user, "Cavalry Tank", 4 );

	assertEquals( 3, available_items( &list, user) );

	add_unit( user, "Helicopter", 9 );

	assertEquals( 4, available_items( &list, user) );

	add_unit( user, "Ninja", 0 );

	assertEquals( 4, available_items( &list, user) );

	add_unit( user, "Guns", 2 );

	assertEquals( 5, available_items( &list, user) );

}

void testUnavailableUnit(){
	t_user* user = create_user();

	t_map_t* list;

	add_unavailable_unit( user, "Artillery" );

	int items = available_items( &list, user);

	assertEquals( 0, items );
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
	if ( testToRun == NULL || strcmp(testToRun, "testNoUnits") == 0 ){
		runTest ( "testNoUnits" , &testNoUnits );

		//runTest ( "testMultipleUnits" , &testMultipleUnits );
		//runTest ( "testUnavailableUnit" , &testUnavailableUnit );
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

	if ( testToRun == NULL || strcmp(testToRun, "testUnavailableUnit") == 0 ){
		runTest ( "testUnavailableUnit" , &testUnavailableUnit );
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

