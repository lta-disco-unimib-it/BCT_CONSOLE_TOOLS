#include <stdlib.h>

typedef struct node{
	double value;
	struct node* next;
} t_node;

double calculateDouble( double inp ){
	double res = inp * 2;
	res += 1;
	res += 1;
	return res;
}

float calculateFloat( float inp ){
	float res = inp * 2;
	res += 1;
	res += 1;
	return res;
}

int main(){
	t_node* pointer = 0;
//	pointer->value = 3.3;
	
	t_node* pointer2 = (t_node *) malloc( sizeof ( t_node ) );
	pointer2->next = 0;

	t_node node;
	node.value = 5.5;

	t_node	nodes[4];
	nodes[0].value = 0;
	nodes[1].value = 1.1;
	nodes[2].value = 2.2;
	nodes[3].value = 3.3;

	nodes[0].next = &nodes[1];
	nodes[1].next = &nodes[2];
	nodes[2].next = 0;
	nodes[3].next = &nodes[0];
	
	t_node*	pnodes[4];
	pnodes[0] = (t_node *) malloc( sizeof ( t_node ) );
	pnodes[1] = (t_node *) malloc( sizeof ( t_node ) );
	pnodes[2] = (t_node *) malloc( sizeof ( t_node ) );
	pnodes[3] = 0;
	
	pnodes[0]->value = -1.1;
	pnodes[1]->value = -2.2;
	pnodes[2]->value = -5.5;
//	pnodes[3]->value = -4.4;

	pnodes[0]->next = pnodes[1];
	pnodes[1]->next = pnodes[2];
	pnodes[2]->next = pnodes[3];
//	pnodes[3]->next = pnodes[0];
	
	t_node (*pointerToArr)[4];
	pointerToArr = 0;

	double x = - 1.1;
	double d = calculateDouble( x );
	d += 1;

	float f = calculateFloat( -2.2 );
	f += 1;

	return 0;
}
