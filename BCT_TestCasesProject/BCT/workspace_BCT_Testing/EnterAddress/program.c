
int global = 0;

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

	f(3);
	p();
	v();
	func(5,7);
	f(3);
}
