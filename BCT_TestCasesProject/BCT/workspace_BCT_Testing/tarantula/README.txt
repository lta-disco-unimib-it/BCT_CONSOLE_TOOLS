#Steps to execute:

#delete coverage data
rm *.ser

#compile with ocverage info
make all

#Executes passing test cases:
bash passing.runall.sh

#Executes failing test cases:
bash failing.runall.sh

#Caculates score
bash score.sh
