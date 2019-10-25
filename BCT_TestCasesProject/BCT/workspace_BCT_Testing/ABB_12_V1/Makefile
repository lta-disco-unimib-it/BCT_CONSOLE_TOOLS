
all:
	g++ -O0 -g3  -I . -I data data/Data.cpp  EmptyException.cpp  Learner.cpp  program.cpp -o ABB_12

BCT:
	cd ../ABB_12_V0/; make ; cd ../ABB_12_V1
	rm -rf ../Analysis_ABB_12
	java -Dbct.monitorPointerToThis=true -Dbct.monitorGlobalVariables=false -Dbct.useEntryPointDetector=true -Dbct.simulateClosingOfFunctions=true -Dbct.filterNotClosed=false GenerateUnitCheckingScripts ../Analysis_ABB_12 /home/BCT/workspace_BCT_Testing/ABB_12_V0 /home/BCT/workspace_BCT_Testing/ABB_12_V0/ABB_12 /home/BCT/workspace_BCT_Testing/ABB_12_V1 /home/BCT/workspace_BCT_Testing/ABB_12_V1/ABB_12
	gdb -batch -silent -n -x /home/BCT/workspace_BCT_Testing/ABB_12_V1/../Analysis_ABB_12/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt --args ../ABB_12_V0/ABB_12
	gdb -batch -silent -n -x /home/BCT/workspace_BCT_Testing/ABB_12_V1/../Analysis_ABB_12/BCT/BCT_DATA/BCT/conf/files/scripts/modifiedSoftware.gdb.config.txt --args ./ABB_12
	java -Djava.util.logging.config.file=logging.properties -Dbct.maxHeap=1024M IdentifyAnomalies ../Analysis_ABB_12
	
