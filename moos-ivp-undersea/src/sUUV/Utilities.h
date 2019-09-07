/*
 * Utilities.h
 *
 *  Created on: 4 Jan 2017
 *      Author: sgerasimou
 */

#ifndef SRC_SUUV_UTILITIES_H_
#define SRC_SUUV_UTILITIES_H_

#include <iostream>

#define ASSERT(condition, message) \
    do { \
        if (! (condition)) { \
            std::cerr << "Assertion `" #condition "` failed in " << __FILE__ << " line " << __LINE__ << ": " << message; \
            std::terminate(); \
        } \
    } while (false)

class Utilities {

public:
    static void writeToFile(const char *filename, const std::string& outputString);

private:
    Utilities();

    virtual ~Utilities();
};

#endif /* SRC_SUUV_UTILITIES_H_ */
