

#GDB_CONFIG=C:\\BCT\\tcas\\versions.experiment\\v6\\Analysis\\BCT\\BCT_DATA\\BCT\\conf\\files\\scripts\\originalSoftware.gdb.config.txt
#GDB="gdb -batch -silent -n -x $GDB_CONFIG --args"
GDB="coverPassing.sh"
rm coveragePassing.ser

$GDB ./mid.exe 3 3 5
$GDB ./mid.exe 1 2 3
$GDB ./mid.exe 3 2 1
$GDB ./mid.exe 5 5 5
$GDB ./mid.exe 5 3 4
