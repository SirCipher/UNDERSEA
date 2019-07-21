@echo off
echo "Compiling UUV.g4"
CALL antlr4 -listener -visitor UUV.g4 -o ../src/main/java/com/type2labs/dsl/uuv/dsl/gen/

echo "Compiling Sensors.g4"
CALL antlr4 -listener -visitor Sensors.g4 -o ../src/main/java/com/type2labs/dsl/uuv/dsl/gen/