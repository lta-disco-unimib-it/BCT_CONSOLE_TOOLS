
GDB_CONFIG=/home/BCT/workspace_BCT_Testing/multiple_exits/Analysis/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt
GDB=gdb -batch -silent -n -x ${GDB_CONFIG} --args

all:
	gcc -O0 -ggdb program.c -o program

analyze: 
	java -Dbct.traceAll=false -Dbct.monitorGlobalVariables=true -Dbct.processPointers=true GenerateDebuggingScripts Analysis . ./program
	${GDB} ./program
