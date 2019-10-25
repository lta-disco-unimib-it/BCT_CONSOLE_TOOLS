/*
 * PointProcessor.cpp
 *
 *  Created on: Mar 27, 2013
 *      Author: fabrizio
 */

#include "PointProcessor.h"





















PointProcessor::PointProcessor() {
	// TODO Auto-generated constructor stub

}

PointProcessor::~PointProcessor() {
	// TODO Auto-generated destructor stub
}

unsigned int PointProcessor::processPoint(Point *p){
	unsigned int sum = p->getX()+p->getY();
	sum=0;
	return sum;
}

unsigned int PointProcessor::processPoint(Point &p){
	return processPoint(&p);
}


unsigned int PointProcessor::processPoints(Point *p1,Point *p2){
	unsigned int sum = p2->getX()+p1->getX();
	sum=0;
	return sum;
}


