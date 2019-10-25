for x in `cat bdci.tests.toIgnore.txt`
do
	mv V0/t/$x V0/t/__$x
	mv V1/t/$x V1/t/__$x
	mv V2/t/$x V2/t/__$x
done

. ./bdci.config.sh



cd $EsempioV0
sed -i 's/CFLAGS = -g -O2 -Wall/CFLAGS = -ggdb -O0 -Wall/' Makefile
make clean;
make || { echo "failed: make $EsempioV0" ; exit 1; }
testFolder="t"
java -cp $CLASSPATH it.unimib.disco.lta.bdci.experiments.git.ExecuteTestsAndRepair $testFolder
cd ..

cd $EsempioV1
sed -i 's/CFLAGS = -g -O2 -Wall/CFLAGS = -ggdb -O0 -Wall/' Makefile
make clean
make || { echo "failed: make $EsempioV0" ; exit 1; }
testFolder="t"
java -cp $CLASSPATH it.unimib.disco.lta.bdci.experiments.git.ExecuteTestsAndRepair $testFolder
cd ..

cd $EsempioV2
sed -i 's/CFLAGS = -g -O2 -Wall/CFLAGS = -ggdb -O0 -Wall/' Makefile
make clean
make || { echo "failed: make $EsempioV0" ; exit 1; }
testFolder="t"
java -cp $CLASSPATH it.unimib.disco.lta.bdci.experiments.git.ExecuteTestsAndRepair $testFolder
cd ..




gitInode=`ls -i V0/git|awk '{print $1}'`
ls -i V0|grep $gitInode|awk '{print $2}'|grep -v '^git$' > bdci.builtin.v0.txt

gitInode=`ls -i V1/git|awk '{print $1}'`
ls -i V1|grep $gitInode|awk '{print $2}'|grep -v '^git$' > bdci.builtin.v1.txt

gitInode=`ls -i V2/git|awk '{print $1}'`
ls -i V2|grep $gitInode|awk '{print $2}'|grep -v '^git$' > bdci.builtin.v2.txt
