/*
 * serverLoop.h
 *
 *  Created on: 4 Jan 2017
 *      Author: sgerasimou
 */

#ifndef CLIENTSERVER_SERVERLOOP_H_
#define CLIENTSERVER_SERVERLOOP_H_

#include "../UUV.h"

void initialiseServer(int portNo);

void *runServer(void *dummyPt);

void *runServer2(UUV uuv);

void closeServer();

long writeData(const std::string& outputStr) ;

#endif /* CLIENTSERVER_SERVERLOOP_H_ */