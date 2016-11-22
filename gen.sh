#!/bin/bash

rm -f all.umple
cat *.umple > all.umple
java -jar umple.jar -g Java all.umple
