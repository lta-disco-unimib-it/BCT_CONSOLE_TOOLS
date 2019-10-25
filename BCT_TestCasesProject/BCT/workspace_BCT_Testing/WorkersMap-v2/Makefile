
BASE_PATH=/home/BCT/workspace_BCT_Testing/WorkersMap
BASE_EXEC=${BASE_PATH}/WorkersMap

all:
	g++ -O0 -g3 src/TestException.cpp  src/WorkersMap.cpp  src/TestException.h  src/WorkersMap.h  src/WorkersMapTest.cpp -o WorkersMap

test:
	./WorkersMap

BCT: all
	cd 
	java -cp bct-console-tools.jar GenerateDebuggingScripts 
	
