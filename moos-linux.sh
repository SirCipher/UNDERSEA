#!/bin/bash

echo "Setting up MOOS for Linux"

sudo apt-get install subversion build-essential g++ cmake xterm libfltk1.3-dev freeglut3-dev libpng-dev libjpeg-dev libxft-dev libxinerama-dev libtiff5-dev

svn co https://oceanai.mit.edu/svn/moos-ivp-aro/trunk/ moos-ivp

cd moos-ivp
svn up -r8525

./build-moos.sh
./build-ivp.sh

echo "Built MOOS"
