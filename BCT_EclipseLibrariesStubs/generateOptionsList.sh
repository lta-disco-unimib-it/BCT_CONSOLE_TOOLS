#!/bin/bash

rm src/bct.system.properties.desc.txt

find ../ -name "*.java" -exec grep -B 1 -hE '///PROPERTIES-DESCRIPTION:|= "bct\.' -r {} \; | sed 's/--//' | grep -A 1 '///' | sed 's/--//' | sed 's/";//' | sed 's/^.*= "//' > src/bct.system.properties.desc.txt


