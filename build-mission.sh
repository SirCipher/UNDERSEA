#!/bin/bash

#get working directory
HOME=`pwd`

if [ $# -eq 0 ]; then
	printf "\nNo mission file provided. Stopping execution!\n\n" $CONFIG_FILE
	exit 0
fi

#MISSION_DIR=moos-ivp/missions/uuvExemplar
MISSION_DIR=moos-ivp-UNDERSEA/missions/uuvExemplar

CONFIG_FILE=$1

CONTROLLER_DIR=UNDERSEA_Controller

BUILD_DIR="$HOME/build"


#-------------------------------------------------------------------
#  Part 1: Create build directory if does not exist
#-------------------------------------------------------------------
if [ ! -d "$BUILD_DIR" ]; then
  mkdir build
fi

#-------------------------------------------------------
#  Part 2: Parse configuration file
#-------------------------------------------------------
# Arg1: configuration file
# Arg2: Controller's directory
# Arg3: Missions directory

printf "Gerenating UUV mission given in %s\n" $CONFIG_FILE

java -jar build/UNDERSEA_DSL.jar $CONFIG_FILE $CONTROLLER_DIR $MISSION_DIR

if [[ $? -eq 0 ]]; then
  printf "Error parsing configuration file. Building aborted!\n"
  exit 0
fi

if [ ! -d "$BUILD_DIR/resources" ]; then
  mkdir $BUILD_DIR/resources
fi

cp $CONTROLLER_DIR/resources/config.properties $BUILD_DIR/resources/

cd $HOME


#-------------------------------------------------------
#  Part 3: Create the .moos and .bhv files.
#-------------------------------------------------------
printf "Creating UUV mission and behaviour files in $BUILD_DIR\n"

#go to missions directory
cd $MISSION_DIR

#create mission
nsplug meta_vehicle.moos targ_uuv.moos

#create behaviour
nsplug meta_vehicle.bhv targ_uuv.bhv


sleep 1
cp targ_uuv.moos $BUILD_DIR/targ_uuv.moos
cp targ_uuv.bhv $BUILD_DIR/targ_uuv.bhv

cd $HOME

printf "\nUUV mission and behaviour files created successfully in $BUILD_DIR\n"
