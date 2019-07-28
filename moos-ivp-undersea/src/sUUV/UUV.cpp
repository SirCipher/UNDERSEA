/************************************************************/
/*    NAME: Simos Gerasimou                                              */
/*    ORGN: MIT                                             */
/*    FILE: UUV.cpp                                        */
/*    DATE:                                                 */
/************************************************************/

#include <iterator>
#include "MBUtils.h"
#include "UUV.h"
#include "Utilities.h"
#include "server/serverLoop.h"
#include <pthread.h>

using namespace std;

//---------------------------------------------------------
// Constructor
//---------------------------------------------------------
UUV::UUV()
{
	m_iterations 			= 0;
	m_timewarp   			= 1;
	m_app_start_time		= MOOSTime(true);
	m_current_iterate		= m_app_start_time;
	m_previous_iterate 		= m_app_start_time;

	M_TIME_WINDOW 			= 10;

	PORT 					= 8888;

	m_uuv_speed				= 4;
}


//---------------------------------------------------------
// Destructor
//---------------------------------------------------------
UUV::~UUV()
{
}


//---------------------------------------------------------
// Procedure: OnStartUp()
//            happens before connection is open
//---------------------------------------------------------
bool UUV::OnStartUp()
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

		  if(param == "NAME") { // get uuv name
			m_uuv_name = value;
		  }
		  else if (param == "SENSORS"){
			  bool handled = handleSensorsNames(value);
		  }
		  else if (param == "TIME_WINDOW"){
			  if (isNumber(value.c_str()))
				  M_TIME_WINDOW = atoi(value.c_str());
		  }
		  else if (param == "PORT"){
			  if (isNumber(value.c_str()))
				  PORT = atoi(value.c_str());
		  }
		  else //throw a configuration warning
			  reportUnhandledConfigWarning(original_line);
		}
	}
	else{
		reportConfigWarning("No configuration block found for " + GetAppName());
	}

	m_timewarp = GetMOOSTimeWarp();

	//init sensors map
	initSensorsMap();

	RegisterVariables();
	return(true);
}


//---------------------------------------------------------
// Procedure: OnConnectToServer
//---------------------------------------------------------
bool UUV::OnConnectToServer()
{
   // register for variables here
   // possibly look at the mission file?
   // m_MissionReader.GetConfigurationParam("Name", <string>);
   // m_Comms.Register("VARNAME", 0);

   //init controller server
	initServer();

   RegisterVariables();
   return(true);
}


//---------------------------------------------------------
// Procedure: RegisterVariables
//---------------------------------------------------------
void UUV::RegisterVariables()
{
	AppCastingMOOSApp::RegisterVariables();      // Add this line

	for (vector<string>::iterator it = m_uuv_sensors.begin();  it != m_uuv_sensors.end(); it++){
		 Register(*it, 0);
	}
}


//---------------------------------------------------------
// Procedure: OnNewMail
//---------------------------------------------------------
bool UUV::OnNewMail(MOOSMSG_LIST &NewMail)
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
		double value  = msg.GetDouble();
		if (find(m_uuv_sensors.begin(), m_uuv_sensors.end(), key) != m_uuv_sensors.end()){

			m_sensors_map[key].newReading(value);
		}
	}

	return(true);
}


//---------------------------------------------------------
// Procedure: Iterate()
//            happens AppTick times per second
//---------------------------------------------------------
bool UUV::Iterate()
{
	AppCastingMOOSApp::Iterate();                  // Add this line

	//do app stuff here
	m_iterations++;

	m_current_iterate = MOOSTime(true);
//	if (m_current_iterate - m_previous_iterate >= M_TIME_WINDOW){

		string outputString = doubleToString(m_current_iterate - GetAppStartTime(), 2) +",";

		//reset sensors
//		for (sensorsMap::iterator it = m_sensors_map.begin();  it != m_sensors_map.end(); it++){
//			outputString += doubleToString(it->second.averageRate,2) +",";
			//reset sensors information
//			it->second.reset();
//		}



		//show visual hints on pMarineViewer
		sendNotifications();

		//log
//		Utilities::writeToFile("log/sensorsRates.csv", outputString);

		m_previous_iterate = m_current_iterate;
//	}

	AppCastingMOOSApp::PostReport();               // Add this line
	return(true);
}




//---------------------------------------------------------
// Procedure: buildReport
//---------------------------------------------------------
bool UUV::buildReport()
{
	m_msgs << "UUV name:\t" << m_uuv_name <<  endl << endl;


	m_msgs << "UUV Sensors (" << m_uuv_sensors.size() << ")" << endl;
	m_msgs << "------------------------------------------------"   << endl;
	for (unsigned i=0; i<m_uuv_sensors.size(); i++){
		m_msgs << m_uuv_sensors.at(i) << endl;
	}

	m_msgs << endl << endl;
	for (sensorsMap::iterator it = m_sensors_map.begin();  it != m_sensors_map.end(); it++){
		m_msgs << it->second.toString() << endl;
	}

	return true;
}


//---------------------------------------------------------
// Procedure: handleSensorsNames
// check if the provided string is in the format SENSOR_1SEart:end:degradationPercentage
// e.g. 50:100:50
//---------------------------------------------------------
bool UUV::handleSensorsNames(string value)
{
	vector<string> v = parseString(removeWhite(value),",");

	//check if all tokens are alphanumerics
	for (vector<string>::iterator it = v.begin();  it != v.end(); it++){
		if (!isAlphaNum(*it))
			reportConfigWarning("Problem with configuring 'SENSORS ="+ value +"': expected alphanumeric but received " + *it);
		else
			//if everything is OK, create a sensor element and add it to the vector
			m_uuv_sensors.push_back(*it);
	}

	return true;
}


//---------------------------------------------------------
// Procedure: initSensorsMap
//---------------------------------------------------------
void UUV::initSensorsMap()
{
	for (vector<string>::iterator it = m_uuv_sensors.begin();  it != m_uuv_sensors.end(); it++){
		Sensor newSensor;
		newSensor.name		 		= *it;
		newSensor.averageRate 		= 0;
		newSensor.numOfReadings 	= 0;
		newSensor.numOfSuccReadings	= 0;
		newSensor.state				= -1;
		newSensor.time				= MOOSTime(true);
		newSensor.other				= 0;
		m_sensors_map[*it] 			= newSensor;
	}

	//Dummy workaround for getting the speed value from controller
	//have a sensor element in sensor map with SPEED name. time is the speed value
	Sensor newSensor;
	newSensor.name		 		= "SPEED";
	newSensor.averageRate 		= 0;
	newSensor.numOfReadings 	= 0;
	newSensor.numOfSuccReadings	= 0;
	newSensor.state				= -1;
	newSensor.time				= 0;
	newSensor.other				= m_uuv_speed;
	m_sensors_map["SPEED"] 		= newSensor;
}


//---------------------------------------------------------
// Procedure: initServer
//---------------------------------------------------------
void UUV::initServer()
{
	initialiseServer(PORT);
	pthread_t thread;
//	int n = pthread_create(&thread, NULL, runServer, NULL);
	int n = pthread_create(&thread, NULL, runServer2,  (void *) &m_sensors_map);

}

//---------------------------------------------------------
// Procedure: sendNotifications
//---------------------------------------------------------
void UUV::sendNotifications()
{
	string yPosition 	= "-50";
	int xMiddlePosition = 75;

	int numOfSensors 	= m_uuv_sensors.size();

	int xStartPosition 	= (m_uuv_sensors.size()/2 * (-50)) + xMiddlePosition;

	int index=0;
	for (sensorsMap::iterator it = m_sensors_map.begin();  it != m_sensors_map.end(); it++){
		 if (it->first !=  "SPEED"){

			int xPosition = xStartPosition + index*50;
			index++;

			string sensorColor = "";
			switch (it->second.state){
				case -1: {
					sensorColor = "red";
							break;
				}
				case 0: {
							sensorColor = "white";
							break;
				}
				case 1: {
							sensorColor = "green";
							break;
				}
				case 2: {
							sensorColor = "orange";
							break;
				}
			}

			Notify("VIEW_MARKER", "type=circle,x=" + intToString(xPosition) +",y=-50,scale=2,label=" + it->first +",color=" + sensorColor + ",width=12");
			Notify("VIEW_MARKER", "type=circle,x=" + intToString(xPosition) +",y=-70,scale=2,msg=avg.rate:"+ doubleToString(it->second.averageRate,2)+ ",label="+it->first+"1,color=darkblue,width=1");
		 }
	}

	//sent speed notification
	m_uuv_speed = m_sensors_map["SPEED"].other;
	Notify("UPDATES_CONSTANT_SPEED", "speed="+doubleToString(m_uuv_speed));
	Notify("VIEW_MARKER", "type=circle,x=" + intToString(xMiddlePosition) +",y=-85,scale=2,msg=speed:"+ doubleToString(m_uuv_speed,2)+ ",label=speed,color=darkblue,width=1");


}
