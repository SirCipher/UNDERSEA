#!/bin/bash

# Get working directory
HOME=`pwd`

if [ $# -eq 0 ]; then
	printf "\nNo mission or sensor file provided. Stopping execution!\n\n"
	exit 0
fi

MISSION_DIR=moos-ivp-UNDERSEA/missions/uuvExemplar

MISSION_CONFIG_FILE=$1
SENSOR_FILE=$2
CONFIG_FILE=$3
CONTROLLER_DIR=UNDERSEA_Controller
BUILD_DIR="$HOME/build"

printf "Mission dir: %s\n" $MISSION_DIR
printf "Mission config file dir: %s\n" $MISSION_CONFIG_FILE
printf "Sensor file dir: %s\n" $SENSOR_FILE
printf "Build dir: %s\n" $BUILD_DIR
printf "Gerenating UUV mission given in %s\n" $MISSION_CONFIG_FILE

java -cp build/UNDERSEA_DSL.jar undersea.ParserEngine $MISSION_CONFIG_FILE $SENSOR_FILE $CONTROLLER_DIR $MISSION_DIR $CONFIG_FILE

printf "\nUUV mission and behaviour files created successfully in $BUILD_DIR\n"
