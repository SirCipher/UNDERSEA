#--------------------------------------------------------
# The CMakeLists.txt for:                       sSensor
# Author(s):                              Simos Gerasimou
#--------------------------------------------------------

# FILE(GLOB SRC *.cpp)

SET(SRC
        Sensor.cpp
        Sensor_Info.cpp
        main.cpp
        Change.cpp
        )

ADD_EXECUTABLE(sTest ${SRC})

TARGET_LINK_LIBRARIES(sTest
        ${MOOS_LIBRARIES}
        mbutil
        m
        pthread)

