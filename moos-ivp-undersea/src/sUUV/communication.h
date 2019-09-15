//
// Created by tom on 31/08/2019.
//

#ifndef IVP_EXTEND_COMMUNICATION_H
#define IVP_EXTEND_COMMUNICATION_H

#include <sys/socket.h>
#include <cstdlib>
#include <cstring>
#include <unistd.h>
#include <sys/types.h>
#include <netdb.h>
#include <csignal>
#include <thread>
#include <iostream>
#include "UUV.h"

static constexpr unsigned short INCOMING_SOCKET_DATA_BUF_SIZE = 4096;

typedef struct {
    UUV *uuv;
    int port;
} Args;

int make_accept_sock(const char *servspec);

void new_connection(Args args);

void run_server(UUV uuv);

std::string prependPort(int port);

void write_data(UUV *uuv, const char* msg);

#endif //IVP_EXTEND_COMMUNICATION_H
