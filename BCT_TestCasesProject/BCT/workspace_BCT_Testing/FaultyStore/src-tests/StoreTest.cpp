/*
 * WorkersMap_test.c
 *
 *  Created on: Nov 25, 2011
 *      Author: usiusi
 */

#include  <string>
#include <iostream>

#include "TestException.h"
#include "Store.h"
#include "StoreHandler.h"

using namespace std;

int allTestCases;
int passingTestCases;
int failingTestCases;

void assertEquals ( void* v1, void* v2  ){
	if ( v1 != v2 ){
		cout << "found " << v2 << " expected " << v1 << endl;
		throw new TestException();
	}
}

void assertEquals ( long v1, long v2  ){
	if ( v1 != v2 ){
		cout << "found " << v2 << " expected " << v1 << endl;
		throw new TestException();
	}
}

void assertEquals ( pair<string,int> v1, pair<string,int> v2 ){
	if ( v1 != v2 ){
		cout << "found <" << v2.first <<","<<v2.second << "> expected <" << v1.first <<","<<v1.second << ">"<< endl;
		throw new TestException();
	}
}

void testNoItems(){

	Store store;

	string item1("MacBookPro-15");

	assertEquals ( -1, store.availableQty(item1) );


}

void testOneItem(){

	Store store;

	string item1("MacBookPro-15");

	store.addItem( item1, 25 );

	assertEquals ( 25, store.availableQty(item1) );


}

void testOneItemNotAvailable(){

	Store store;

	string item1("MacBookPro-15");

	store.addItem( item1, 0 );

	assertEquals ( 0, store.availableQty(item1) );


}



void testTwoItems(){

	Store store;

	string item1("MacBookPro-15");
	string item2("DellInspiron-17");

	store.addItem( item1, 3 );
	store.addItem( item2, 2 );

	assertEquals ( 3, store.availableQty(item1) );
	assertEquals ( 2, store.availableQty(item2) );

}


bool __itemscomparator(pair<string,int>& l,pair<string,int>& r){
	return l.first < r.first;
}

void testHandlerOneItemAvailable(){

	StoreHandler storeHandler;
	Store store;

	string item1("MacBookPro-17");

	store.addItem( item1, 37 );




	list<string> chainItems;
	chainItems.push_back(item1);

	std::list<std::pair<std::basic_string<char,std::char_traits<char> ,std::allocator<char> > ,int> ,std::allocator<std::pair<std::basic_string<char,std::char_traits<char> ,std::allocator<char> > ,int> > > *resultsToShow;

	resultsToShow = storeHandler.availableItems(store,chainItems);

	assertEquals ( 2, resultsToShow->size());



	resultsToShow->sort(__itemscomparator);

	list< pair<string,int> >::iterator it;

	it = resultsToShow->begin();

	assertEquals ( make_pair(item1,37), *it);

	it++;
	assertEquals ( make_pair("Total",37), *it);
}

void testHandlerOneOfTwoItemsAvailable(){

	StoreHandler storeHandler;
	Store store;

	string item1("DellLatitude-17");
	string item2("DellLatitude-21");

	store.addItem( item1, 7 );
	store.addItem( item2, 0 );




	list<string> chainItems;
	chainItems.push_back(item1);
	chainItems.push_back(item2);

	std::list<std::pair<std::basic_string<char,std::char_traits<char> ,std::allocator<char> > ,int> ,std::allocator<std::pair<std::basic_string<char,std::char_traits<char> ,std::allocator<char> > ,int> > > *resultsToShow;

	resultsToShow = storeHandler.availableItems(store,chainItems);

	assertEquals ( 2, resultsToShow->size());



	resultsToShow->sort(__itemscomparator);

	list< pair<string,int> >::iterator it;

	it = resultsToShow->begin();

	assertEquals ( make_pair(item1,7), *it);

	it++;
	assertEquals ( make_pair("Total",7), *it);
}


void testHandlerAllItemsAvailable(){

	StoreHandler storeHandler;
	Store store;

	string item1("Battery_DellInspiron-1526");
	string item2("Battery_HP-MU09");
	string item3("MacBookPro-15");
	string item4("DellInspiron-15");
	string item5("DellInspiron-17");
	string item6("DellInspiron-21");


	store.addItem( item1, 2 );
	store.addItem( item2, 3 );
	store.addItem( item3, 40 );
	store.addItem( item4, 65 );
	store.addItem( item5, 6 );
	store.addItem( item6, 1 );




	list<string> chainItems;
	chainItems.push_back(item1);
	chainItems.push_back(item2);
	chainItems.push_back(item3);
	chainItems.push_back(item4);
	chainItems.push_back(item5);
	chainItems.push_back(item6);

	std::list<std::pair<std::basic_string<char,std::char_traits<char> ,std::allocator<char> > ,int> ,std::allocator<std::pair<std::basic_string<char,std::char_traits<char> ,std::allocator<char> > ,int> > > *resultsToShow;

	resultsToShow = storeHandler.availableItems(store,chainItems);

	assertEquals ( 7, resultsToShow->size());



	resultsToShow->sort(__itemscomparator);

	list< pair<string,int> >::iterator it;

//	for ( it = resultsToShow->begin(); it != resultsToShow->end(); it++ ){
//		cout << (*it).first << endl;
//	}

	it = resultsToShow->begin();

	assertEquals ( make_pair(item1,2), *it);

	it++;
	assertEquals ( make_pair(item2,3), *it);

	it++;
	assertEquals ( make_pair(item4,65), *it);

	it++;
	assertEquals ( make_pair(item5,6), *it);

	it++;
	assertEquals ( make_pair(item6,1), *it);

	it++;
	assertEquals ( make_pair(item3,40), *it);

	it++;
	assertEquals ( make_pair("Total",117), *it);
}


void testHandlerSomeItemsAvailable(){

	StoreHandler storeHandler;
	Store store;

	string item1("DellInspironBattery-1526");
	string item2("HPBattery-MU09");
	string item3("MacBookPro-15");
	string item4("DellInspiron-15");
	string item5("DellInspiron-17");


//	store.addItem( item1, 2 );
//	store.addItem( item2, 3 );
	store.addItem( item3, 2 );
	store.addItem( item4, 65 );
	store.addItem( item5, 6 );




	list<string> chainItems;
	chainItems.push_back(item1);
	chainItems.push_back(item2);
	chainItems.push_back(item3);
	chainItems.push_back(item4);
	chainItems.push_back(item5);


	std::list<std::pair<std::basic_string<char,std::char_traits<char> ,std::allocator<char> > ,int> ,std::allocator<std::pair<std::basic_string<char,std::char_traits<char> ,std::allocator<char> > ,int> > > *resultsToShow;

	resultsToShow = storeHandler.availableItems(store,chainItems);

	assertEquals ( 4, resultsToShow->size());

	resultsToShow->sort(__itemscomparator);

	list< pair<string,int> >::iterator it;

	it = resultsToShow->begin();


	assertEquals ( make_pair(item4,65), *it);

	it++;
	assertEquals ( make_pair(item5,6), *it);

	it++;
	assertEquals ( make_pair(item3,2), *it);

	it++;
	assertEquals ( make_pair("Total",73), *it);
}





void runTest( string testCaseName, void (*testCase)() ){
	cout << "Running: " << testCaseName << endl;
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
	if ( testToRun == NULL || (*testToRun).compare("testNoItems") == 0 ){
		runTest ( "testNoItems" , &testNoItems );
	}
	if ( testToRun == NULL || (*testToRun).compare("testOneItem") == 0 ){
		runTest ( "testOneItem", testOneItem );
	}
	if ( testToRun == NULL || (*testToRun).compare("testOneItemNotAvailable") == 0 ){
		runTest ( "testOneItemNotAvailable", testOneItemNotAvailable );
	}
	if ( testToRun == NULL || (*testToRun).compare("testTwoItems") == 0 ){
		runTest ( "testTwoItems", testTwoItems );
	}
	if ( testToRun == NULL || (*testToRun).compare("testHandlerOneItemAvailable") == 0  ){
		runTest ( "testHandlerOneItemAvailable", testHandlerOneItemAvailable );
	}
	if ( testToRun == NULL || (*testToRun).compare("testHandlerOneOfTwoItemsAvailable") == 0  ){
		runTest ( "testHandlerOneOfTwoItemsAvailable", testHandlerOneOfTwoItemsAvailable );
	}
	if ( testToRun == NULL || (*testToRun).compare("testHandlerAllItemsAvailable") == 0  ){
		runTest ( "testHandlerAllItemsAvailable", testHandlerAllItemsAvailable );
	}
	if ( testToRun == NULL || (*testToRun).compare("testHandlerSomeItemsAvailable") == 0  ){
		runTest ( "testHandlerSomeItemsAvailable", testHandlerSomeItemsAvailable );
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




