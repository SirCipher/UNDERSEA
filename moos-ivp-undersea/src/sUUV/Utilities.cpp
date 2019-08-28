/*
 * Utilities.cpp
 *
 *  Created on: 4 Jan 2017
 *      Author: sgerasimou
 */

#include "Utilities.h"
#include <fstream>

using namespace std;


Utilities::Utilities() = default;

Utilities::~Utilities() = default;


//---------------------------------------------------------
// Procedure: writeToFile(string filename, string outputString)
//
//---------------------------------------------------------
void Utilities::writeToFile(const char *filename, const string &outputString) {
    ofstream myfile;
    myfile.open(filename, ios::app);
    myfile << outputString << "\n";
    myfile.close();
}

