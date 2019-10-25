/*
 * Point.h
 *
 *  Created on: Mar 27, 2013
 *      Author: fabrizio
 */

#ifndef POINT_H_
#define POINT_H_

class Point {
	int x;
	int y;

public:
	Point(int _x, int _y) : x(_x),y(_y) {};
	virtual ~Point();

	void setX(int x){ this->x = x; };

	void setY(int y){ this->y = y; };

	int getX(){ return x; };

	int getY(){ return y; };
};

#endif /* POINT_H_ */
