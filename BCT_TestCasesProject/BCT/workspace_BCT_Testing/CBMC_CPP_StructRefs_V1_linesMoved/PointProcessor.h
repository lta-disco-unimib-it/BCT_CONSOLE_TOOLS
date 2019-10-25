/*
 * PointProcessor.h
 *
 *  Created on: Mar 27, 2013
 *      Author: fabrizio
 */

#ifndef POINTPROCESSOR_H_
#define POINTPROCESSOR_H_

#include "Point.h"

class PointProcessor {
public:
	PointProcessor();
	virtual ~PointProcessor();

	unsigned int processPoint(Point *p);

	unsigned int processPoint(Point &p);

	unsigned int processPoints(Point *p1,Point *p2);

};

#endif /* POINTPROCESSOR_H_ */
