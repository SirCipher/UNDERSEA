#!/bin/bash

cd undersea-agent
# TODO: Check return code
./gradlew agent:build agent:fatJar --stacktrace -x test
cd ..

cp undersea-agent/agent/build/libs/agent.jar .

if [[ "$OSTYPE" == "linux-gnu" ]]; then
    export LD_LIBRARY_PATH=libraries/MATLAB/v95/sys/os/glnxa64/:libraries/MATLAB/v95/bin/glnxa64/:libraries/MATLAB/v95/runtime/glnxa64/:libraries/repo/prism/linux/
    java -Djava.library.path=libraries/repo/ortools:libraries/repo/prism/linux/ -jar agent.jar resources/runner.properties
elif [[ "$OSTYPE" == "darwin"* ]]; then
    export DYLD_LIBRARY_PATH=libraries/MATLAB/v95/sys/os/maci64/:libraries/MATLAB/v95/bin/maci64/:libraries/MATLAB/v95/extern/bin/maci64/:libraries/repo/prism/osx/
    java -Djava.library.path=libraries/repo/ortools:libraries/repo/prism/osx/ -jar agent.jar resources/runner.properties
else
	echo "OS not supported yet due to MOOS not supporting it"
	exit 1
fi

# Just in case the JVM crashed...
pkill -f .moos -9