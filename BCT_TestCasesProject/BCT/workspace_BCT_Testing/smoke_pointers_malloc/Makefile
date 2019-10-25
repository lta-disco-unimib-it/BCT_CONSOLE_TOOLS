

CC=gcc

all:
	${CC} -Wall -g3 -O0 -o program program.c


BCT-monitor:
	${CC} -Wall -g3 -O0 -o program program.c
	rm -rf An
	#java -Dbct.monitoring.linesToMonitor=program.c:20 -Dbct.customOnly=true GenerateDebuggingScripts An . ./program
	java -cp ${CLASSPATH} -Dbct.completeMonitoring=false -Dbct.monitoring.linesToMonitor=program.c:18,program.c:23 -Dbct.customOnly=true GenerateDebuggingScripts An . ./program
	gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt --args ./program -1
	#gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt --args ./program 1 -1
	#gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt --args ./program 1 2 3 7 8 9 -1
	#gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt --args ./program 1 -1 1
	#gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt --args ./program 2 3 4 -1
	gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.toVerify.txt --args ./program 1

BCT:
	java -cp ${CLASSPATH} -Dbct.inference.skipArrays=true -Dbct.inference.daikon.config.file=conf.txt -Dbct.inference.undoDaikonOptimizations=false -Djava.logging.file.path=logging.properties -Dbct.check.removeInvalidIO=true -Dbct.output.folder=bct_reports -Dbct.maxHeap=1024M IdentifySegFaultAnomalies An

