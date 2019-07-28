#!/bin/bash

echo "Setting up MOOS for OSX"

brew install cmake subversion wget libtiff fltk

svn co https://oceanai.mit.edu/svn/moos-ivp-aro/trunk/ moos-ivp

cd moos-ivp
svn up -r8525

./build-moos.sh
./build-ivp.sh