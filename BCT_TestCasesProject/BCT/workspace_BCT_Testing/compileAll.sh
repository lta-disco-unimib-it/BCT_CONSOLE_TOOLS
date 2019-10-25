

for x in `find ./ -maxdepth 1 -type d`
do
	echo $x
	cd $x
	make
	cd ..
done
