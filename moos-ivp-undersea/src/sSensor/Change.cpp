/************************************************************/
/*    NAME: Simos Gerasimou                                 */
/*    ORGN: University of York, UK                          */
/*    FILE: Failure.cpp                                     */
/*    DATE: 2016                                            */
/************************************************************/

#include "MBUtils.h"
#include "Change.h"

using namespace std;

Change::Change(double start, double finish, double degradation)
{
	m_start  		= start;
	m_finish 	  	= finish;
	m_change 	= degradation==0 ? 0.0000001 : degradation ;
}


Change::~Change()
{
}


double Change::getStartTime()
{
	return m_start;
}


double Change::getFinishTime()
{
	return m_finish;
}


double Change::getChange()
{
	return m_change;
}


string Change::toString()
{
	string str = doubleToString(m_start,2) +"\t\t"+ doubleToString(m_finish,2) +"\t\t"+ doubleToString(m_change,2);
	return str;
}

