# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.14

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/apps/CLion/ch-0/191.7479.33/CLion.app/Contents/bin/cmake/mac/bin/cmake

# The command to remove a file.
RM = /Applications/apps/CLion/ch-0/191.7479.33/CLion.app/Contents/bin/cmake/mac/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug

# Include any dependencies generated for this target.
include src/sSensor/CMakeFiles/sSensor.dir/depend.make

# Include the progress variables for this target.
include src/sSensor/CMakeFiles/sSensor.dir/progress.make

# Include the compile flags for this target's objects.
include src/sSensor/CMakeFiles/sSensor.dir/flags.make

src/sSensor/CMakeFiles/sSensor.dir/Sensor.cpp.o: src/sSensor/CMakeFiles/sSensor.dir/flags.make
src/sSensor/CMakeFiles/sSensor.dir/Sensor.cpp.o: ../src/sSensor/Sensor.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object src/sSensor/CMakeFiles/sSensor.dir/Sensor.cpp.o"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/sSensor.dir/Sensor.cpp.o -c /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/Sensor.cpp

src/sSensor/CMakeFiles/sSensor.dir/Sensor.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/sSensor.dir/Sensor.cpp.i"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/Sensor.cpp > CMakeFiles/sSensor.dir/Sensor.cpp.i

src/sSensor/CMakeFiles/sSensor.dir/Sensor.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/sSensor.dir/Sensor.cpp.s"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/Sensor.cpp -o CMakeFiles/sSensor.dir/Sensor.cpp.s

src/sSensor/CMakeFiles/sSensor.dir/Sensor_Info.cpp.o: src/sSensor/CMakeFiles/sSensor.dir/flags.make
src/sSensor/CMakeFiles/sSensor.dir/Sensor_Info.cpp.o: ../src/sSensor/Sensor_Info.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object src/sSensor/CMakeFiles/sSensor.dir/Sensor_Info.cpp.o"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/sSensor.dir/Sensor_Info.cpp.o -c /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/Sensor_Info.cpp

src/sSensor/CMakeFiles/sSensor.dir/Sensor_Info.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/sSensor.dir/Sensor_Info.cpp.i"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/Sensor_Info.cpp > CMakeFiles/sSensor.dir/Sensor_Info.cpp.i

src/sSensor/CMakeFiles/sSensor.dir/Sensor_Info.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/sSensor.dir/Sensor_Info.cpp.s"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/Sensor_Info.cpp -o CMakeFiles/sSensor.dir/Sensor_Info.cpp.s

src/sSensor/CMakeFiles/sSensor.dir/main.cpp.o: src/sSensor/CMakeFiles/sSensor.dir/flags.make
src/sSensor/CMakeFiles/sSensor.dir/main.cpp.o: ../src/sSensor/main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object src/sSensor/CMakeFiles/sSensor.dir/main.cpp.o"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/sSensor.dir/main.cpp.o -c /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/main.cpp

src/sSensor/CMakeFiles/sSensor.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/sSensor.dir/main.cpp.i"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/main.cpp > CMakeFiles/sSensor.dir/main.cpp.i

src/sSensor/CMakeFiles/sSensor.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/sSensor.dir/main.cpp.s"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/main.cpp -o CMakeFiles/sSensor.dir/main.cpp.s

src/sSensor/CMakeFiles/sSensor.dir/Change.cpp.o: src/sSensor/CMakeFiles/sSensor.dir/flags.make
src/sSensor/CMakeFiles/sSensor.dir/Change.cpp.o: ../src/sSensor/Change.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building CXX object src/sSensor/CMakeFiles/sSensor.dir/Change.cpp.o"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/sSensor.dir/Change.cpp.o -c /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/Change.cpp

src/sSensor/CMakeFiles/sSensor.dir/Change.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/sSensor.dir/Change.cpp.i"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/Change.cpp > CMakeFiles/sSensor.dir/Change.cpp.i

src/sSensor/CMakeFiles/sSensor.dir/Change.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/sSensor.dir/Change.cpp.s"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor/Change.cpp -o CMakeFiles/sSensor.dir/Change.cpp.s

# Object files for target sSensor
sSensor_OBJECTS = \
"CMakeFiles/sSensor.dir/Sensor.cpp.o" \
"CMakeFiles/sSensor.dir/Sensor_Info.cpp.o" \
"CMakeFiles/sSensor.dir/main.cpp.o" \
"CMakeFiles/sSensor.dir/Change.cpp.o"

# External object files for target sSensor
sSensor_EXTERNAL_OBJECTS =

../bin/sSensor: src/sSensor/CMakeFiles/sSensor.dir/Sensor.cpp.o
../bin/sSensor: src/sSensor/CMakeFiles/sSensor.dir/Sensor_Info.cpp.o
../bin/sSensor: src/sSensor/CMakeFiles/sSensor.dir/main.cpp.o
../bin/sSensor: src/sSensor/CMakeFiles/sSensor.dir/Change.cpp.o
../bin/sSensor: src/sSensor/CMakeFiles/sSensor.dir/build.make
../bin/sSensor: /Users/MUDKIPZ/Desktop/PACS/moos-ivp/build/MOOS/MOOSCore/lib/libMOOS.a
../bin/sSensor: src/sSensor/CMakeFiles/sSensor.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Linking CXX executable ../../../bin/sSensor"
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/sSensor.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
src/sSensor/CMakeFiles/sSensor.dir/build: ../bin/sSensor

.PHONY : src/sSensor/CMakeFiles/sSensor.dir/build

src/sSensor/CMakeFiles/sSensor.dir/clean:
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor && $(CMAKE_COMMAND) -P CMakeFiles/sSensor.dir/cmake_clean.cmake
.PHONY : src/sSensor/CMakeFiles/sSensor.dir/clean

src/sSensor/CMakeFiles/sSensor.dir/depend:
	cd /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/src/sSensor /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor /Users/MUDKIPZ/Desktop/PACS/UNDERSEA/moos-ivp-UNDERSEA/cmake-build-debug/src/sSensor/CMakeFiles/sSensor.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : src/sSensor/CMakeFiles/sSensor.dir/depend

