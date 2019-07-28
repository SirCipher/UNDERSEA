#!/bin/bash

echo "Setting up MATLAB Runtime 2018b"

ROOT="$(pwd)"
REMOTE=""

mkdir MATLAB

cd MATLAB
MLR="$(pwd)"

cd ..

mkdir matlabinstall
cd matlabinstall

if [[ "$OSTYPE" == "linux-gnu" ]]; then
	  REMOTE="http://ssd.mathworks.com/supportfiles/downloads/R2018b/deployment_files/R2018b/installers/glnxa64/MCR_R2018b_glnxa64_installer.zip"
	  ZIP="MCR_R2018b_glnxa64_installer.zip"
	  FOLDER="or-tools_Ubuntu-18.04-64bit_v7.2.6977"
elif [[ "$OSTYPE" == "darwin"* ]]; then
	  REMOTE="http://ssd.mathworks.com/supportfiles/downloads/R2018b/deployment_files/R2018b/installers/maci64/MCR_R2018b_maci64_installer.dmg.zip"
	  ZIP="MCR_R2018b_maci64_installer.dmg.zip"
	  FOLDER="or-tools_MacOsX-10.14.5_v7.2.6977"
else
	echo "OS not supported yet due to MOOS not supporting it"
	exit 0
fi

echo "Fetching " $REMOTE
wget $REMOTE --no-check-certificate

mkdir MATLAB
echo "Unzipping " $ZIP
unzip $ZIP -d MATLAB

echo "Removing " $ZIP
rm $ZIP

echo "Setting up Matlab R2018b"
cd MATLAB

if [[ "$OSTYPE" == "linux-gnu" ]]; then
    ./install -mode silent -agreeToLicense yes -destinationFolder $MLR

    if [[ $? != 0 ]]; then
        echo "Failed to install MATLAB runtime"
    else
        echo "Installed MATLAB Runtime"
        # Attempt to fix segmentation violation
        cd ../../MATLAB/
        mv v95/sys/os/glnxa64/libstdc++.so.6 v95/sys/os/glnxa64/libstdc++.so.6.old
        cd ..
    fi

elif [[ "$OSTYPE" == "darwin"* ]]; then
    hdiutil attach MCR_R2018b_maci64_installer.dmg

    cd /Volumes/MCR_R2018b_maci64_installer/InstallForMacOSX.app/Contents/MacOS/

    echo "Installing MATLAB Runtime to " $MLR

    ./InstallForMacOSX -mode silent -agreeToLicense yes-mode silent -agreeToLicense yes -destinationFolder $MLR

    if [[ $? != 0 ]]; then
        echo "Failed to install MATLAB runtime"
    else
        echo "Installed MATLAB Runtime"
    fi

    hdiutil detach /Volumes/MCR_R2018b_maci64_installer/
fi

echo "Deleting MATLAB build directory"

rm -rf matlabinstall

cd $ROOT