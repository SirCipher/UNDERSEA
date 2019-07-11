/*
 * Utilities.cpp
 *
 *  Created on: 4 Jan 2017
 *      Author: sgerasimou
 */

#include "Utilities.h"
#include <fstream>

using namespace std;


Utilities::Utilities()
{}

Utilities::~Utilities()
{}


//---------------------------------------------------------
// Procedure: writeToFile(string filename, string outputString)
//
//---------------------------------------------------------
void Utilities::writeToFile(string filename, string outputString)
{
	ofstream myfile;
	myfile.open (filename, ios::app);
	myfile << outputString << "\n";
	myfile.close();
	return;
}

