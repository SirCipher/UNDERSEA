## Linux [![Build Status](https://type2labs.visualstudio.com/UNDERSEA/_apis/build/status/SirCipher.UNDERSEA?branchName=master&jobName=Linux)](https://type2labs.visualstudio.com/UNDERSEA/_build/latest?definitionId=8&branchName=master) Mac [![Build Status](https://travis-ci.com/SirCipher/UNDERSEA.svg?branch=master)](https://travis-ci.com/SirCipher/UNDERSEA)

---

UNDERSEA has primarily been developed using Ubuntu 18.04 LTS, however, it has also been tested on Mac OS, and Windows 10 using the Windows Subsystem for Linux (WSL) - without any GUI applications from MOOS. 

Running UNDERSEA using Windows:
---
UNDERSEA on Windows 10 has only been tested using CLion and IntelliJ, not as a target release. In addition to this, WSL does not natively support GUI applications - while support can be added through an XServer, WSL sockets fail to bind - so pMarineViewer does not launch. Instructions to setup for development are as follows:
To setup UNDERSEA on Windows:
- Setup the machine as per the [instructions](https://docs.microsoft.com/en-us/windows/wsl/install-win10) for Ubuntu 18.04 LTS
- Install [CLion](https://www.jetbrains.com/clion/)
- [Setup CLion with WSL](https://www.jetbrains.com/help/clion/how-to-use-wsl-development-environment-in-clion.html)
- Install `dos2unix` on WSL with: `sudo apt install dos2unix`
- Checkout this repository: `git clone https://github.com/SirCipher/UNDERSEA.git`
- Change to the repository: `cd UNDERSEA`
- `setup.sh` will initialise the development environment, however, the line endings are incorrect and they need to be converted to the correct form: `dos2unix moos-linux.sh ortools.sh matlab.sh setup.sh runner.sh`
- In order to develop using Windows for the UNDERSEA platform but without the MOOS subsystem, Google OR Tools and Matlab must be installed to the libraries directory that resides in this folder. [OR Tools](https://github.com/google/or-tools/releases/download/v7.3/or-tools_VisualStudio2017-64bit_v7.3.7083.zip) must be installed to `libraries/ortools` and [MATLAB](https://ssd.mathworks.com/supportfiles/downloads/R2018b/deployment_files/R2018b/installers/glnxa64/MCR_R2018b_glnxa64_installer.zip) to `libraries/MATLAB`.

If you are planning to extend UNDERSEA and continue developing MOOS applications then the development environment can be setup such that IntelliJ/CLion/scripts launch the application on the WSL and no Java process issues will be encountered - as the agents are launched via terminal commands and these will be executed on Windows.


Case study 1
----
Demonstrating automatic task decomposition, distribution and execution to local cluster clients. A leader is automatically elected and distributes the mission to the clients.

The video also demonstrates the leader being killed and a new one elected. The new mission is re-decomposed and distributed to the available clients. The same functionality is demonstrated by a follower failing.

[![Case study 1](https://img.youtube.com/vi/hwb0acLLqaw/0.jpg)](https://www.youtube.com/watch?v=hwb0acLLqaw)

Case study 2
----
Demonstrating automatic task decomposition, distribution and execution to local cluster clients. An agent is assigned a mission but then fails, notifies its leader and the leader assigns the remaining agents in the cluster to the mission.

The video also demonstrates the leader being killed and a new one elected. The new mission is re-decomposed and distributed to the available clients. The same functionality is demonstrated by a follower failing.

[![Case study 2](https://img.youtube.com/vi/fK5BTo5zOIo/0.jpg)](https://www.youtube.com/watch?v=fK5BTo5zOIo)
