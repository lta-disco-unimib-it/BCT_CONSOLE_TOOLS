/*
 * WorkersMap.cpp
 *
 *  Created on: Nov 25, 2011
 *      Author: usiusi
 */

#include "WorkersMap.h"

WorkersMap::WorkersMap() {
	// TODO Auto-generated constructor stub

}

WorkersMap::~WorkersMap() {
	// TODO Auto-generated destructor stub
}


long WorkersMap::getSalary( string workerId ){

	if ( ! isWorker( workerId ) ){
		#ifdef NEWVERSION
		return -1;
		#else
		return NULL;
		#endif
	}

	return workers.find( workerId )->second;

}


void WorkersMap::addWorker( string personId, long annualSalary ){
	workers.insert( pair<string,long>(personId, annualSalary) );
}

bool WorkersMap::isWorker( string personId ){
	if ( workers.count( personId ) == 0 ){
		return false;
	}
	return true;
}

long WorkersMap::getAverageSalary( list<string> personIds ){

	list<string>::iterator i;
	long totalSalary = 0;
	int workers = 0;

	for(i=personIds.begin(); i != personIds.end(); ++i){
		long salary = getSalary( *i );
		if ( salary == 0 ){ //if salary is NULL the person does not work
			continue;
		}
		totalSalary += salary;
		workers++;
	}

	if ( workers  == 0 ){
		return -1;
	}

	return totalSalary/workers;
}


