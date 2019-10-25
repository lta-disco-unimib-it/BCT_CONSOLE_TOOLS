
int f(int x){
	if ( x < 0 ){
		x = -x;
	}

	return x;
}

int main(){
	int i;

	for ( i = 0; i < 10; i++ ){
		f(i);
	}

	return 0;
}
