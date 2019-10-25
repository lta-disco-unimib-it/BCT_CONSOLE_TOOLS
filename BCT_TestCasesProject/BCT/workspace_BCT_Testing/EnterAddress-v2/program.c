
int global = -1;

int p(){
	return global;
}

void v(){
}

void func(int x, int y){
}

int f(int x){
	int y = x;
	return y;
}

int main(){

	f(4);
	p();
	v();
	func(9,17);
	f(5);
}
