
. ./bdci.config.sh

cd $EsempioV0

dateStartMonitoring=$(date +"%s")

$PIN_HOME/intel64/bin/pinbin -p32 $PIN_HOME/ia32/bin/pinbin -t $DATA/AnalysisV0/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt.probes/bdciProbe.so -o $DATA/AnalysisV0/BCT/BCT_DATA/BCT/validTraces/ -- ./test


dateEndMonitoring=$(date +"%s")
diff1=$(($dateEndMonitoring-$dateStartMonitoring))
echo "Time monitoring V0: $diff1"

cd ..






