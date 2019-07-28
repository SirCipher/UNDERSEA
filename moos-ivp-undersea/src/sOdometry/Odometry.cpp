/************************************************************/
/*    NAME: Simos Gerasimou                                              */
/*    ORGN: MIT                                             */
/*    FILE: Odometry.cpp                                        */
/*    DATE:                                                 */
/************************************************************/

#include <iterator>
#include "MBUtils.h"
#include "Odometry.h"

using namespace std;

//---------------------------------------------------------
// Constructor
//---------------------------------------------------------
Odometry::Odometry()
{
  m_iterations 		= 0;
  m_timewarp   		= 1;
  m_first_reading 	= true;
  m_current_x 		= 0;
  m_current_y 		= 0;
  m_previous_x 		= 0;
  m_previous_y 		= 0;
  m_total_distance 	= 0;
}


//---------------------------------------------------------
// Destructor
//---------------------------------------------------------
Odometry::~Odometry()
{
}


//---------------------------------------------------------
// Procedure: OnNewMail
//---------------------------------------------------------
bool Odometry::OnNewMail(MOOSMSG_LIST &NewMail)
{
  AppCastingMOOSApp::OnNewMail(NewMail);        // Add this line

  MOOSMSG_LIST::iterator p;
   
  for(p=NewMail.begin(); p!=NewMail.end(); p++) {
    CMOOSMsg &msg = *p;

#if 0 // Keep these around just for template
    string key   = msg.GetKey();
    string comm  = msg.GetCommunity();
    double dval  = msg.GetDouble();
    string sval  = msg.GetString(); 
    string msrc  = msg.GetSource();
    double mtime = msg.GetTime();
    bool   mdbl  = msg.IsDouble();
    bool   mstr  = msg.IsString();
#endif

    string key   = msg.GetKey();
    if (key == "NAV_X"){
    	m_previous_x = m_current_x;
    	m_current_x  = msg.GetDouble();
    }
    else if (key == "NAV_Y"){
    	m_previous_y = m_current_y;
		m_current_y  = msg.GetDouble();
	}
  }

   return(true);
}


//---------------------------------------------------------
// Procedure: OnConnectToServer
//---------------------------------------------------------
bool Odometry::OnConnectToServer()
{
   // register for variables here
   // possibly look at the mission file?
   // m_MissionReader.GetConfigurationParam("Name", <string>);
   // m_Comms.Register("VARNAME", 0);
	
   RegisterVariables();
   return(true);
}


//---------------------------------------------------------
// Procedure: Iterate()
//            happens AppTick times per second
//---------------------------------------------------------
bool Odometry::Iterate()
{
  AppCastingMOOSApp::Iterate();

  m_iterations++;

  double distance = -1;

  if (m_first_reading){
	  m_first_reading = false;
	  distance =  sqrt(pow(m_current_x,2.0) + pow(m_current_y,2.0));
  }
  else{
	  distance =  sqrt(pow(m_current_x-m_previous_x,2.0) + pow(m_current_y-m_previous_y,2.0));
  }

  m_total_distance += distance;

  Notify("ODOMETRY_DIST", m_total_distance);
  Notify("ODOMETRY_X", m_current_x);
  Notify("ODOMETRY_Y", m_current_y);

  AppCastingMOOSApp:PostReport();
  return(true);
}


//---------------------------------------------------------
// Procedure: OnStartUp()
//            happens before connection is open
//---------------------------------------------------------
bool Odometry::OnStartUp()
{
  AppCastingMOOSApp::OnStartUp();               // Add this line

  list<string> sParams;
  m_MissionReader.EnableVerbatimQuoting(false);
  if(m_MissionReader.GetConfiguration(GetAppName(), sParams)) {
    list<string>::iterator p;
    for(p=sParams.begin(); p!=sParams.end(); p++) {
      string original_line = *p;
      string param = stripBlankEnds(toupper(biteString(*p, '=')));
      string value = stripBlankEnds(*p);
      
      if(param == "FOO") {
        //handled
      }
      else if(param == "BAR") {
        //handled
      }
    }
  }
  
  m_timewarp = GetMOOSTimeWarp();

  RegisterVariables();	
  return(true);
}


//---------------------------------------------------------
// Procedure: RegisterVariables
//---------------------------------------------------------
void Odometry::RegisterVariables()
{
  AppCastingMOOSApp::RegisterVariables();      // Add this line

  Register("NAV_X", 0);
  Register("NAV_Y", 0);
}


//---------------------------------------------------------
// Procedure: buildReport
//---------------------------------------------------------
bool Odometry::buildReport()
{
	m_msgs << "Distance traveled: " << m_total_distance << endl;
	m_msgs << "Current coordinates (x,y): (" << m_current_x <<","<< m_current_y <<")"  << endl;

	return true;
}
