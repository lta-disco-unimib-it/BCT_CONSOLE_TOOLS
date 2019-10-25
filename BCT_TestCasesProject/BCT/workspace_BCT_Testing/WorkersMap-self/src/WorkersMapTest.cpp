/*
 * WorkersMap_test.c
 *
 *  Created on: Nov 25, 2011
 *      Author: usiusi
 */

#include  <string>
#include <iostream>

#include "TestException.h"
#include "WorkersMap.h"

using namespace std;

int allTestCases;
int passingTestCases;
int failingTestCases;

void assertEquals ( long v1, long v2  ){
	if ( v1 != v2 ){
		cout << "found " << v2 << " expected " << v1 << endl;
		throw new TestException();
	}
}

void testNoWorker(){

	WorkersMap map;

	string worker1("PSTFRZ83D6YETZD");

	assertEquals ( 0, map.getSalary(worker1) );


}

void testOneWorker(){

	WorkersMap map;

	string worker1("PSTFRZ83D6YETZD");

	map.addWorker( worker1, 50000 );

	assertEquals ( 50000, map.getSalary(worker1) );


}

void testOneWorker_NoSalary(){

	WorkersMap map;

	string worker1("PSTFRZ83D6YETZD");

	map.addWorker( worker1, 0 );

	assertEquals ( 0, map.getSalary(worker1) );


}



void testTwoWorkers(){

	WorkersMap map;

	string worker1("PSTFRZ83D6YETZD");

	map.addWorker( worker1, 50000 );


	string worker2("PBTFRZ83D6YETZD");

	map.addWorker( worker2, 10000 );

	assertEquals ( 50000, map.getSalary(worker1) );
	assertEquals ( 10000, map.getSalary(worker2) );


}


void testGetAverageSalary_many(){

	WorkersMap map;

	string worker1("PSTFRZ83D6YETZD");
	map.addWorker( worker1, 50000 );

	string worker2("PBTFRZ83D6YETZD");
	map.addWorker( worker2, 10000 );

	string worker3("JKTFRZ83D6YETZD");
	map.addWorker( worker3, 15000 );
	
	string worker4("RBTFRZ83D6YETZD");
	map.addWorker( worker4, 19000 );
	
	string worker5("ABTFRZ83D6YETZD");
	map.addWorker( worker5, 11000 );

	string worker6("CRLLNN83D6YETZD");
	map.addWorker( worker6, 90000 );

	string worker7("ABTFTZ83D6YETZD");
	map.addWorker( worker7, 13000 );

	list<string> workers;
	workers.push_back(worker1);
	workers.push_back(worker2);
	workers.push_back(worker3);
	workers.push_back(worker4);
	workers.push_back(worker5);
	workers.push_back(worker7);

	assertEquals ( (50000+10000+15000+19000+11000+13000)/6, map.getAverageSalary(workers) );


}



void testGetAverageSalary_mix(){
	WorkersMap map;

	string worker1("MRTFRZ83D6YETZD");

	string worker2("PBTFRZ83D6YETZD");
	map.addWorker( worker2, 10000 );

	string worker3("PSTFRZ83D6YETZD");
	map.addWorker( worker3, 50000 );

	list<string> workers;
	workers.push_back(worker1);
	workers.push_back(worker2);
	workers.push_back(worker3);

	assertEquals ( 30000,
			map.getAverageSalary(workers) );


}



void runTest( string testCaseName, void (*testCase)() ){
	allTestCases++;
	try {
		testCase();
		passingTestCases++;
		cout << "Test passed: " << testCaseName << endl;
	} catch ( ... ) {
		failingTestCases++;
		cout << "Test failed: " << testCaseName << endl;
	}
}

void run( string *testToRun ){
	if ( testToRun == NULL || (*testToRun).compare("testNoWorker") == 0 ){
		runTest ( "testNoWorker" , &testNoWorker );
	}
	if ( testToRun == NULL || (*testToRun).compare("testOneWorker") == 0 ){
		runTest ( "testOneWorker", testOneWorker );
	}
	if ( testToRun == NULL || (*testToRun).compare("testOneWorker_NoSalary") == 0 ){
		runTest ( "testOneWorker_NoSalary", testOneWorker_NoSalary );
	}
	if ( testToRun == NULL || (*testToRun).compare("testTwoWorkers") == 0 ){
		runTest ( "testTwoWorkers", testTwoWorkers );
	}
//	if ( testToRun == NULL || (*testToRun).compare("testGetAverageSalary_nobody") == 0  ){
//		runTest ( "testGetAverageSalary_nobody", testGetAverageSalary_nobody );
//	}
	if ( testToRun == NULL || (*testToRun).compare("testGetAverageSalary_many") == 0  ){
		runTest ( "testGetAverageSalary_many", testGetAverageSalary_many );
	}
	if ( testToRun == NULL || (*testToRun).compare("testGetAverageSalary_mix") == 0  ){
		runTest ( "testGetAverageSalary_mix", testGetAverageSalary_mix );
	}
}

int main(int argc,char* argv[]){
	cout << "Running test cases for WorkersMap class" << endl;
	int exitCode = 0;
	if ( argc == 1 ){
		run(NULL);
	} else {
		for ( int i = 1; i < argc; i++){
			string s(argv[i]);
			run(&s);
		}
	}

	if ( allTestCases == passingTestCases ){
		cout << "All test cases were successfully executed" << endl;
	} else {
		exitCode = 1;
		cout << "Test terminated with failures" << endl;
	}

	cout << "test cases executed: " << allTestCases << endl;

	cout << "test cases passed: " << passingTestCases << endl;

	cout << "test cases failed: " << failingTestCases << endl;

	return exitCode;
}




