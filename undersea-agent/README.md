# UNDERSEA
An Exemplar for Engineering Self-Adaptive Unmanned Underwater Vehicles

Additional information can be found on the [UNDERSEA webpage](http://www-users.cs.york.ac.uk/simos/UNDERSEA/)


The repository comprises the following directories:
* **UNDERSEA_Controller**: An Eclipse Maven project that contains the source for developing a new controller

* **UNDERSEA_DSL**: An Eclipse Maven project that contains the source for modifying and extending UNDERSEA DSL

* **missions**: Example missions specified in UNDERSEA DSL

* **moos-ivp-UNDERSEA**: Necessary UNDERSEA components (Sensor and UUV) for MOOS-IvP

Also, the repository comprises the following shell scripts:
* **build-controller.sh**: Script for building UNDERSEA_Controller.jar.
<br /> Run ```./build-controller.sh```

* **build-dsl.sh**: Script for building UNDERSEA_DSL.jar.
<br /> Run ```./build-dsl.sh```

* **build-mission.sh**: Script for building the necessary mission files.
<br /> Run ```./build-mission.sh missionPath```

* **build-missionParser.sh**: Script for building the UNDERSEA DSL. Execute this script only when you modify UNDERSEA DSL.
<br /> Run ```./build-mission.sh missionPath```

* **clean.sh**: Script for cleaning either the build directory (using flag `-r`) or any pending MOOS-IvP processes (using flag `-k`).
<br /> Run ```./clean.sh -r``` or ```./clean.sh -k```

* **launch.sh**: Script for launching a mission (including the controller and the controlled system)
<br /> Run ```./launch.sh```

* **memoryMonitor.sh**: Script for launching visualvm and connecting the UNDERSEA_Controller process with the memory monitoring tool)
<br /> Run ```./memoryMonitor.sh```


--


Should you have any comments, suggestions or questions, or to report a bug, please email simos.gerasimou-at-york.ac.uk
