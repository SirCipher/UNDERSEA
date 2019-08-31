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
#include <asm/ioctls.h>
#include <stropts.h>
#include <csignal>
#include <thread>
#include <iostream>
#include "UUV.h"

typedef struct {
    UUV *uuv;
    int port;
} Args;

int make_accept_sock(const char *servspec);

bool isclosed(int sock);

void new_connection(Args args);

void run_server(UUV uuv);

const char *prependPort(int port);

void init_outbound(UUV uuv);

#endif //IVP_EXTEND_COMMUNICATION_H
