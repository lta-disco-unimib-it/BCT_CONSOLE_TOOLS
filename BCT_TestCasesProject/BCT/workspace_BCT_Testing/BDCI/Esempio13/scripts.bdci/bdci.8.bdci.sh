. ./bdci.config.sh




dateStartGDB=$(date +"%s")




java -cp $CLASSPATH it.unimib.disco.lta.bdci.BDCIPreprocess $DATA


java -cp $CLASSPATH it.unimib.disco.lta.bdci.BDCI $DATA &> bdci.out


dateEndBDCI=$(date +"%s")


diff2=$(($dateEndBDCI-$dateStartGDB))

echo "Time BDCI: $diff2"
