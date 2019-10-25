rem This script can be used to show finite state automata

echo off
rem This is the path to BCT jar
set BCT_JAR="..\lib\bct-b20080331.jar"
rem This is the java command, if java not works put the complete path instead of java
rem Example:
rem 		set JAVA_CMD=Z:\opt\java\bin\java
rem
set JAVA_CMD=java

%JAVA_CMD% -cp %BCT_JAR% tools.ShowFSA %1
