#--------------------------------------------------------
# The CMakeLists.txt for:                       sOdometry
# Author(s):                              Simos Gerasimou
#--------------------------------------------------------

# FILE(GLOB SRC *.cpp)

SET(SRC
  Odometry.cpp
  Odometry_Info.cpp
  main.cpp
)



ADD_EXECUTABLE(sOdometry ${SRC})

TARGET_LINK_LIBRARIES(sOdometry
   ${MOOS_LIBRARIES}
   mbutil
   m
   pthread)

