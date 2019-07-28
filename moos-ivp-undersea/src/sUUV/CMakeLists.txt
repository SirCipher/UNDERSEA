#--------------------------------------------------------
# The CMakeLists.txt for:                       sUUV
# Author(s):                              Simos Gerasimou
#--------------------------------------------------------

# FILE(GLOB SRC *.cpp)

SET(SRC
  UUV.cpp
  UUV_Info.cpp
  main.cpp
  Utilities.cpp
  server/serverLoop.cpp
)



ADD_EXECUTABLE(sUUV ${SRC})

TARGET_LINK_LIBRARIES(sUUV
   ${MOOS_LIBRARIES}
   mbutil
   m
   pthread)

