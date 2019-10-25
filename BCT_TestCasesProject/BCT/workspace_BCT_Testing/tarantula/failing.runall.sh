

#GDB_CONFIG=C:\\BCT\\tcas\\versions.experiment\\v6\\Analysis\\BCT\\BCT_DATA\\BCT\\conf\\files\\scripts\\originalSoftware.gdb.config.toVerify.txt
#GDB="gdb -batch -silent -n -x $GDB_CONFIG --args"
GDB="coverFailing.sh"
rm coverageFailing.ser

$GDB ./mid.exe 2 1 3
