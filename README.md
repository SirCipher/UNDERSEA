## Linux [![Build Status](https://type2labs.visualstudio.com/UNDERSEA/_apis/build/status/SirCipher.UNDERSEA?branchName=master&jobName=Linux)](https://type2labs.visualstudio.com/UNDERSEA/_build/latest?definitionId=8&branchName=master) Mac [![Build Status](https://travis-ci.com/SirCipher/UNDERSEA.svg?branch=master)](https://travis-ci.com/SirCipher/UNDERSEA) Docs [![Build Status](https://readthedocs.org/projects/undersea/badge/?version=latest)](https://undersea.readthedocs.io/en/latest/?badge=latest) 

---

# UNDERSEA
An Exemplar for Engineering Self-Adaptive Vehicles, prototyped using the Mission Oriented Operating Suite ([MOOS](https://oceanai.mit.edu/moos-ivp/pmwiki/pmwiki.php)). 

Additional information on the original UNDERSEA application can be found on the [UNDERSEA webpage](http://www-users.cs.york.ac.uk/simos/UNDERSEA/).

Further information on the project, architecture, tutorials are currently in progress. This project is currently under heavy development (as part of my dissertation) and is not recommended to be used in a production environment. The full report and research paper will be published in the coming months. Documentation will be available [here](https://undersea.readthedocs.io/en/latest/index.html) or if you wish to dive straight in, the first guide is available [here](https://undersea.readthedocs.io/en/latest/tutorial1.html).

---

To setup and use on Ubuntu, or Mac OS, checkout the project, run `setup.sh` and wait for all the dependencies to pull.

----

The repository comprises the following directories:

- **libraries**: populated by running `setup.sh`
- **resources**: case studies
- **moos-ivp-UNDERSEA**: Necessary UNDERSEA components (Sensor and UUV) for MOOS-IvP
- **undersea-agent**:
    - **agent**: a sample implementation of the software, built for MOOS. This includes a DSL parser for the agent configuration files.
    - **common**: common functionality for all submodules
    - **missionmanager**: A mission manager and planner for agent missions
    - **monitor**: a monitoring application for the cluster
    - **prospect**: a custom [Raft](https://raft.github.io/raft.pdf) implementation for the cluster
    - **seachain**: a todo application which will migrate the distributed logging system to an Ethereum network
    - **tutorials**: contains a handful of tutorials that demonstrate the functionality of the project

----
UNDERSEA has primarily been developed using Ubuntu 18.04 LTS, however, it has also been tested on Mac OS, and Windows 10 using the Windows Subsystem for Linux (WSL) - without any GUI applications from MOOS. 

---
Running UNDERSEA using Windows WSL:
---

UNDERSEA on Windows 10 has only been tested using CLion and IntelliJ, though not as a target release. In addition to this, WSL does not natively support GUI applications - while support can be added through an XServer, WSL sockets fail to bind - so pMarineViewer does not launch and run correctly. Instructions to setup for development are as follows:
To setup UNDERSEA on Windows:
- Setup the machine as per the [instructions](https://docs.microsoft.com/en-us/windows/wsl/install-win10) for Ubuntu 18.04 LTS
- Install [CLion](https://www.jetbrains.com/clion/)
- [Setup CLion with WSL](https://www.jetbrains.com/help/clion/how-to-use-wsl-development-environment-in-clion.html)
- Install `dos2unix` on WSL with: `sudo apt install dos2unix`
- Checkout this repository: `git clone https://github.com/SirCipher/UNDERSEA.git`
- Change to the repository: `cd UNDERSEA`
- `setup.sh` will initialise the development environment, however, the line endings are incorrect and they need to be converted to the correct form: `dos2unix moos-linux.sh ortools.sh matlab.sh setup.sh runner.sh`

If you are planning to extend UNDERSEA and continue developing MOOS applications then the development environment can be setup such that IntelliJ/CLion/scripts launch the application on the WSL and no Java process issues will be encountered - as the agents are launched via terminal commands and these will be executed on Windows.

Developing on Windows:
---
- In order to develop using Windows for the UNDERSEA platform but without the MOOS subsystem, Google OR Tools and Matlab must be installed to the libraries directory that resides in this folder. [OR Tools](https://github.com/google/or-tools/releases/download/v7.3/or-tools_VisualStudio2017-64bit_v7.3.7083.zip) must be installed to `libraries/ortools` and [MATLAB](https://ssd.mathworks.com/supportfiles/downloads/R2018b/deployment_files/R2018b/installers/glnxa64/MCR_R2018b_glnxa64_installer.zip) to `libraries/MATLAB`.

---
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
