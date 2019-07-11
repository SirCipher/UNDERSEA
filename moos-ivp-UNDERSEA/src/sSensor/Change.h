/************************************************************/
/*    NAME: Simos Gerasimou                                 */
/*    ORGN: University of York, UK                          */
/*    FILE: Failure.h                                       */
/*    DATE: 2016                                            */
/************************************************************/


#ifndef SRC_SSENSOR_CHANGE_H_
#define SRC_SSENSOR_CHANGE_H_

#include <string>

class Change {
	public:
		Change(double start, double finish, double degradation);
		virtual ~Change();

		std::string toString();
		double getStartTime();
		double getFinishTime();
		double getChange();

	private:
		double m_start; 		//failure starting time
		double m_finish;		//failure finishing time
		double m_change;		//new rate
};

#endif /* SRC_SSENSOR_CHANGE_H_ */
