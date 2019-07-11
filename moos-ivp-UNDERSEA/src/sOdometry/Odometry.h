/************************************************************/
/*    NAME: Simos Gerasimou                                              */
/*    ORGN: MIT                                             */
/*    FILE: Odometry.h                                          */
/*    DATE:                                                 */
/************************************************************/

#ifndef Odometry_HEADER
#define Odometry_HEADER

//#include "MOOS/libMOOS/MOOSLib.h"
#include "MOOS/libMOOS/Thirdparty/AppCasting/AppCastingMOOSApp.h"

class Odometry : public AppCastingMOOSApp
{
 public:
   Odometry();
   ~Odometry();
   bool buildReport();

 protected:
   bool OnNewMail(MOOSMSG_LIST &NewMail);
   bool Iterate();
   bool OnStartUp();

   bool OnConnectToServer();
   void RegisterVariables();


 private: // Configuration variables

 private: // State variables
   unsigned int m_iterations;
   double       m_timewarp;

   bool m_first_reading;
   double m_current_x;
   double m_current_y;
   double m_previous_x;
   double m_previous_y;
   double m_total_distance;
};

#endif 
