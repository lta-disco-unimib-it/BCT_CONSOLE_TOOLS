. ./bdci.config.sh

PROG=git




echo PATH:$PATH

dateStartMonitoring=$(date +"%s")



pushd $EsempioV2
P=$PATH
echo "TEST EXECUTION"
cd t
make
cd ..
pwd
popd
pwd

dateEndMonitoring=$(date +"%s")
diff1=$(($dateStartMonitoring-$dateEndMonitoring))
echo "Time monitoring V2: $diff1"

