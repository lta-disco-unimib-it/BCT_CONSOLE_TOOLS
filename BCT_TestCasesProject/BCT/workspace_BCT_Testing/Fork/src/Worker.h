
#ifndef WORKER_H_
#define WORKER_H_

class Worker {
private:
	int state;

public:
	int calculate(int x, int t);

	Worker(){ state=0; };

	virtual ~Worker();


};

#endif /* WORKER_H_ */
