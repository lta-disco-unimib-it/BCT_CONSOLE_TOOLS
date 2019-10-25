
#include "Point.h"
#include "PointProcessor.h"
#include <cstdlib>

unsigned int processPoint( Point *point ){
	Point* refer = point;
	unsigned int sum = point->getX()+point->getY();
	sum=0;
	return sum;
}

int main(int argc, char* argv[]){
	PointProcessor pp;

	int x = atoi( argv[1] );
	int y = atoi( argv[2] );

	if ( x < 0 || y < 0 ){
		return 1;
	}

	Point p(x,y);
	processPoint(&p);
	pp.processPoint(p);
	pp.processPoint(&p);

	return 0;
}
