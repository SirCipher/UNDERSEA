#!/bin/bash

echo "Setting up Google OR Tools"

ROOT="$(pwd)"

REMOTE=""
ZIP=""
FOLDER=""

if [[ "$OSTYPE" == "linux-gnu" ]]; then
  if [[ `lsb_release -rs` == "19.04" ]]; then
    REMOTE="https://github.com/google/or-tools/releases/download/v7.2/or-tools_ubuntu-19.04_v7.2.6977.tar.gz"
    ZIP="or-tools_ubuntu-19.04_v7.2.6977.tar.gz"
    FOLDER="or-tools_Ubuntu-19.04-64bit_v7.2.6977"
  elif [[ `lsb_release -rs` == "18.04" ]]; then
    REMOTE="https://github.com/google/or-tools/releases/download/v7.2/or-tools_ubuntu-18.04_v7.2.6977.tar.gz"
    ZIP="or-tools_ubuntu-18.04_v7.2.6977.tar.gz"
    FOLDER="or-tools_Ubuntu-18.04-64bit_v7.2.6977"
  elif [[ `lsb_release -rs` == "16.04" ]]; then
    REMOTE="https://github.com/google/or-tools/releases/download/v7.2/or-tools_ubuntu-16.04_v7.2.6977.tar.gz"
    ZIP="or-tools_ubuntu-16.04_v7.2.6977.tar.gz"
    FOLDER="or-tools_Ubuntu-16.04-64bit_v7.2.6977"
  else 
    echo "Unsupported Linux distribution"
    exit 1
  fi

elif [[ "$OSTYPE" == "darwin"* ]]; then
  REMOTE="https://github.com/google/or-tools/releases/download/v7.2/or-tools_MacOsX-10.14.5_v7.2.6977.tar.gz"
  ZIP="or-tools_MacOsX-10.14.5_v7.2.6977.tar.gz"
  FOLDER="or-tools_MacOsX-10.14.5_v7.2.6977"
else
	echo "OS not supported yet due to MOOS not supporting it"
	exit 1
fi

echo "Fetching " $REMOTE
wget $REMOTE --no-check-certificate

echo "Unzipping " $ZIP
tar -xvzf $ZIP

echo "Removing " $ZIP
rm $ZIP

mkdir -p repo/ortools/
echo "Moving files to repo/ortools/"
mv $FOLDER/lib/* repo/ortools/

echo "Removing " $FOLDER
rm -rf $FOLDER
