/*
 * serverLoop.h
 *
 *  Created on: 4 Jan 2017
 *      Author: sgerasimou
 */

#ifndef CLIENTSERVER_SERVERLOOP_H_
#define CLIENTSERVER_SERVERLOOP_H_

#include "../UUV.h"

void initialiseServer (int portNo);

void *runServer (void *dummyPt);

void *runServer2 (void *m_sensors_map);

void closeServer();

#endif /* CLIENTSERVER_SERVERLOOP_H_ */
