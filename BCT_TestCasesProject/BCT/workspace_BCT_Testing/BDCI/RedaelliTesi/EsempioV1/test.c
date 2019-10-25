#include <stdio.h>


float logDaSottrazione(float,float);

float sottrazione(float,float);

float logaritmo(float);

void assert(int x){};

int main(){
	float x=1; 
	float y=2; 
	
	float result; 

	result=logDaSottrazione(x, y); 
	assert(result == 0);
	x=0.5;
	y=0.9;
	result=logDaSottrazione(x, y); 
	printf("%f\n",result); 
	assert(result == -0.3979400);
	x=5;
	y=5;
	result=logDaSottrazione(x, y); assert(result == 0);
	x=2;
	y=1;
	result=logDaSottrazione(x, y); assert(result == 0);
	x=0.4;
	y=10;
	result=logDaSottrazione(x, y); assert(result == 0.9822712);
	x=100;
	y=100;
	result=logDaSottrazione(x, y); assert(result == 0);
	x=4.5;
	y=4.2;
	result=logDaSottrazione(x, y); assert(result == -0.5822878);
	x=0.6;
	y=0.6;
	result=logDaSottrazione(x, y); assert(result == 0);

	return 0;
}
