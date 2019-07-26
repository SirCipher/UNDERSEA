#!/bin/bash

REMOTE=""
ZIP=""
FOLDER=""

if [[ "$OSTYPE" == "linux-gnu" ]]; then
  REMOTE="https://github.com/google/or-tools/releases/download/v7.2/or-tools_ubuntu-18.04_v7.2.6977.tar.gz"
  ZIP="or-tools_ubuntu-18.04_v7.2.6977.tar.gz"
  FOLDER="or-tools_Ubuntu-18.04-64bit_v7.2.6977"
elif [[ "$OSTYPE" == "darwin"* ]]; then
  REMOTE="https://github.com/google/or-tools/releases/download/v7.2/or-tools_MacOsX-10.14.5_v7.2.6977.tar.gz"
  ZIP="or-tools_MacOsX-10.14.5_v7.2.6977.tar.gz"
  FOLDER="or-tools_MacOsX-10.14.5_v7.2.6977"
else
	printf "%s not supported yet due to MOOS not supporting it" $OSTYPE
	exit 0
fi

echo "Fetching " $REMOTE
wget $REMOTE
echo $ZIP
echo "Unzipping " $ZIP
tar -xvzf $ZIP

echo "Removing " $ZIP
rm $ZIP

mkdir --parents repo/ortools/
echo "Moving files to repo/ortools/"
mv $FOLDER/lib/* repo/ortools/

echo "Removing " $FOLDER
rm -rf $FOLDER