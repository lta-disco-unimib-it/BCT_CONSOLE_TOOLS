

CC=gcc

all:
	${CC} -Wall -g3 -O0 -o program program.c


BCT-monitor:
	${CC} -Wall -g3 -O0 -o program program.c
	rm -rf An
	java GenerateDebuggingScripts An . ./program
	gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt --args ./program -
	gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt --args ./program 1 -
	gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt --args ./program 1 2 3 7 8 9 -
	gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt --args ./program 1 - 1
	gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt --args ./program 2 3 4 -
	gdb -batch -silent -n -x An/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.toVerify.txt --args ./program 1

BCT:
	java -Djava.util.logging.config.file=logging.properties -Dbct.inference.undoDaikonOptimizations=true -Dbct.check.removeInvalidIO=true -Dbct.output.folder=bct_reports -Dbct.maxHeap=1024M IdentifySegFaultAnomalies An

