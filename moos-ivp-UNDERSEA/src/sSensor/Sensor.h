/************************************************************/
/*    NAME: Simos Gerasimou                                 */
/*    ORGN: University of York, UK                          */
/*   FILE: Sensor.h                                         */
/*    DATE: 2016                                            */
/************************************************************/

#ifndef Sensor_HEADER
#define Sensor_HEADER

#include "MOOS/libMOOS/Thirdparty/AppCasting/AppCastingMOOSApp.h"

#include "Change.h"

class Sensor : public AppCastingMOOSApp
{
	 public:
	   Sensor();
	   ~Sensor();
	   bool buildReport();

	 protected:
	   bool OnNewMail(MOOSMSG_LIST &NewMail);
	   bool Iterate();
	   bool OnConnectToServer();
	   bool OnStartUp();
	   void RegisterVariables();

	 private:
	   bool handleChange(std::string value);



	 private: // Configuration variables
	   double m_nominal_rate; //parameter defined in moos file
	   std::string m_sensor_name; //sensor name
	   double m_reliability; //sensor reliability
	   std::vector<Change> m_changes;


	 private: // State variables
	   unsigned int m_iterations;
	   double       m_timewarp;
};

#endif 
