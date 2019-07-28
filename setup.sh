#!/bin/bash

ORTOOLS="no"
MATLAB="no"
MOOS="no"
CLEAN="no"

print_usage_and_exit()
{
    printf "Options:                                \n"
    printf "  --help, -h                            \n"
    printf "  --all                                 \n"
    printf "    Build all libraries (no clean)      \n"
    printf "  --moos                                \n"
    printf "    Fetch and build moos                \n"
    printf "  --matlab                              \n"
    printf "    Fetch and install MATLAB runtime    \n"
    printf "  --ortools                             \n"
    printf "    Fetch Google OR Tools               \n"
    printf "  --clean                               \n"
    printf "    Empty libraries directory           \n"
    exit 1
}

for ARGI; do
    if [ "${ARGI}" = "--moos" ] ; then
        MOOS="yes"
    elif [ "${ARGI}" = "--matlab" ] ; then
        MATLAB="yes"
    elif [ "${ARGI}" = "--ortools" ] ; then
        ORTOOLS="yes"
    elif [ "${ARGI}" = "--clean" ] ; then
        CLEAN="yes"
    elif [ "${ARGI}" = "--all" ] ; then
        MOOS="yes"
        MATLAB="yes"
        ORTOOLS="yes"
    elif [ "${ARGI}" = "--help" -o "${ARGI}" = "-h" ] ; then
        print_usage_and_exit;
    fi
done

if [ "$#" = 0 ] ; then
    print_usage_and_exit;
fi

echo "Detected OSTYPE: " $OSTYPE

if [ ${CLEAN} = "yes" ] ; then
    rm -rf libraries
    echo "AUTO GENERATED DIRECTORY. ANY FILES WILL BE LOST" >libraries/README.txt
fi

mkdir libraries

cd libraries

if [ ${ORTOOLS} = "yes" ] ; then
    ./../ortools.sh
fi

if [ ${MATLAB} = "yes" ] ; then
    ./../matlab.sh
fi

if [ ${MOOS} = "yes" ] ; then
    if [[ "$OSTYPE" == "linux-gnu" ]]; then
        ./../moos-linux.sh
    elif [[ "$OSTYPE" == "darwin"* ]]; then
        ./../moos-osx.sh
    else
        echo "OS not supported yet due to MOOS not supporting it"
    fi
fi
