#!/bin/bash

#get working directory
HOME=`pwd`

BUILD_DIR="$HOME/build"

CONTROLLER="UNDERSEA_Controller.jar"
MOOS="targ_uuv.moos"
BHV="targ_uuv.bhv"
PROPERTIES="resources/config.properties"

if [[ "$OSTYPE" == "linux-gnu" ]]; then
  export LD_LIBRARY_PATH=$BUILD_DIR/repo/prism/linux/
elif [[ "$OSTYPE" == "darwin"* ]]; then
	export DYLD_LIBRARY_PATH=$BUILD_DIR/repo/prism/osx/
else 
	printf "%s prism files not implemented yet..." $OSTYPE
	exit 0
fi

#-------------------------------------------------------------------
#  Part 1: Check for and handle command-line arguments
#-------------------------------------------------------------------
#TODO
for ARGI; do
    if [ "${ARGI}" = "--help" -o "${ARGI}" = "-h" ] ; then
    	printf "%s [SWITCHES]                       \n" $0
    	printf "Switches:                           \n"
    	printf "  --help, -h                        \n"
            printf "  --debug,   -d                     \n"
            printf "  --release, -r                     \n"
    	printf "Notes:                              \n"
    	printf " (1) All other command line args will be passed as args    \n"
    	printf "     to \"make\" when it is eventually invoked.            \n"
    	printf " (2) For example -k will continue making when/if a failure \n"
    	printf "     is encountered in building one of the subdirectories. \n"
    	printf " (3) For example -j2 will utilize a 2nd core in the build  \n"
    	printf "     if your machine has two cores. -j4 etc for quad core. \n"
    	exit 0;
    elif [ "${ARGI}" = "--debug" -o "${ARGI}" = "-d" ] ; then
        BUILD_TYPE="Debug"
    elif [ "${ARGI}" = "--release" -o "${ARGI}" = "-r" ] ; then
        BUILD_TYPE="Release"
    elif [ "${ARGI}" = "--build" -o "${ARGI}" = "-b" ]; then
      BUILD="yes"
    else
  		printf "Bad Argument: %s \n" $ARGI
  		exit 0
    fi
done


#-------------------------------------------------------
#  Part 2: Check if all necessary components exist
#-------------------------------------------------------
if [ ! -d "$BUILD_DIR" ]; then
  printf "\nERROR: build directory does not exist\n"
  printf "\tRun ./build-mission.sh & ./build-controller.sh and try again.\n\n"
  exit 0
fi

cd $BUILD_DIR

ERROR=0

if [ ! -e "$CONTROLLER" ]; then
  printf "\nERROR: %s does not exist in build directory\n" $CONTROLLER
  ERROR="1"
fi
if [ ! -e "$MOOS" ]; then
  printf "\nERROR: File %s does not exist in build directory\n" $MOOS
  ERROR="1"
fi
if [ ! -e "$BHV" ]; then
  printf "\nERROR: File %s does not exist in build directory\n" $BHV
  ERROR="1"
fi
if [ ! -e "$PROPERTIES" ]; then
  printf "\nERROR: Properties %s does not exist\n" $PROPERTIES
  ERROR="1"
fi

if [ "$ERROR" -eq "1" ]; then
  printf "\nLaunching UUV aborted! Fix the errors and try again.\n"
  printf "\tRun ./build-mission.sh & ./build-controller.sh and try again.\n\n"
  exit 0
fi




#-------------------------------------------------------
#  Part 3: Launch the shoreside, the UUV & its controller
#-------------------------------------------------------
#go to missions directory
cd $BUILD_DIR

#start uuv simulator
printf "\nLaunching the mission!\n"
pAntler targ_uuv.moos >& /dev/null &

sleep 3


#start controller

(time java -jar UNDERSEA_Controller.jar) 2> CPU.txt
EXIT_VALUE=$?

if [ ! -d "log" ]; then
  mkdir log
fi

CPU_FILE=CPU_`date`.txt
mv CPU.txt "$CPU_FILE"
mv "$CPU_FILE" log/

if [[ $EXIT_VALUE -eq 1 ]]; then
  cd $HOME
  ./clean.sh -k
fi

printf "\nLog data is build/log\n"


