/*
 * Utilities.h
 *
 *  Created on: 4 Jan 2017
 *      Author: sgerasimou
 */

#ifndef SRC_SUUV_UTILITIES_H_
#define SRC_SUUV_UTILITIES_H_

#include <iostream>

class Utilities {

	public:
		static void writeToFile(std::string filename, std::string outputString);

	private:
		Utilities();
		virtual ~Utilities();
};

#endif /* SRC_SUUV_UTILITIES_H_ */
