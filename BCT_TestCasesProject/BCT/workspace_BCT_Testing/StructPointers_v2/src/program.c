#include <stdlib.h>

typedef struct node{
	int value;
	struct node* next;
} t_node;

int main(){
	t_node* pointer = 0;
	//pointer->value = 3;
	
	t_node* pointer2 = (t_node *) malloc( sizeof ( t_node ) );
	pointer2->next = 0;

	t_node node;
	node.value = 5;

	t_node	nodes[4];
	nodes[0].value = 0;
	nodes[1].value = 1;
	nodes[2].value = 2;
	nodes[3].value = 3;

	nodes[0].next = &nodes[1];
	nodes[1].next = &nodes[2];
	nodes[2].next = 0;
	nodes[3].next = &nodes[0];
	
	t_node*	pnodes[4];
	pnodes[0] = (t_node *) malloc( sizeof ( t_node ) );
	pnodes[1] = (t_node *) malloc( sizeof ( t_node ) );
	pnodes[2] = (t_node *) malloc( sizeof ( t_node ) );
	pnodes[3] = 0;
	
	pnodes[0]->value = -1;
	pnodes[1]->value = -2;
	pnodes[2]->value = -5;
	//pnodes[3]->value = -4;

	pnodes[0]->next = pnodes[1];
	pnodes[1]->next = pnodes[2];
	pnodes[2]->next = pnodes[3];
	//pnodes[3]->next = pnodes[0];
	
	t_node (*pointerToArr)[4];
	pointerToArr = 0;

	return 0;
}
