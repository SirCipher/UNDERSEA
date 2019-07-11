#!/bin/bash

echo "Compiling UUV.g4"
java -jar /usr/local/lib/antlr-4.7.2-complete.jar -listener -visitor UUV.g4 -o ../src/main/java/uuv/dsl/gen/

echo "Compiling Sensors.g4"
java -jar /usr/local/lib/antlr-4.7.2-complete.jar -listener -visitor Sensors.g4 -o ../src/main/java/uuv/dsl/gen/