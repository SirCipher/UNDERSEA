#!/bin/bash

HOME=`pwd`
DSL_DIR=UNDERSEA_DSL
BUILD_DIR="$HOME/build"

#-------------------------------------------------------------------
#  Part 1: Create build directory if does not exist
#-------------------------------------------------------------------
if [ ! -d "$BUILD_DIR" ]; then
  mkdir build
fi

#-------------------------------------------------------------------
#  Part 2: Create the DSL
#-------------------------------------------------------------------

cd $DSL_DIR

mvn clean package

JAR="target/UNDERSEA_DSL.jar"

if [ -e "$JAR" ]; then
  cp $JAR $BUILD_DIR/
fi

printf "\nUUV DSL created successfully in $BUILD_DIR\n"

cd $HOME