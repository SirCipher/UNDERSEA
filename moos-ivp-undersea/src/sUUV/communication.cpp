/**
 * Adapted from https://stackoverflow.com/questions/25091148/single-tcp-ip-server-that-handles-multiple-clients-in-c
 */

#include "communication.h"

using namespace std;

bool serverRunning = true;


int make_accept_sock(const char *servspec) {
    const int one = 1;
    struct addrinfo hints = {};
    struct addrinfo *res = 0, *ai = 0, *ai4 = 0;
    char *node = strdup(servspec);
    char *service = strrchr(node, ':');
    int sock;

    hints.ai_family = PF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_flags = AI_PASSIVE;

    *service++ = '\0';
    getaddrinfo(*node ? node : "0::0", service, &hints, &res);
    free(node);

    for (ai = res; ai; ai = ai->ai_next) {
        if (ai->ai_family == PF_INET6) break;
        else if (ai->ai_family == PF_INET) ai4 = ai;
    }
    ai = ai ? ai : ai4;

    sock = socket(ai->ai_family, SOCK_STREAM, 0);
    setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &one, sizeof(one));
    bind(sock, ai->ai_addr, ai->ai_addrlen);

    if (listen(sock, 256) != 0) {
        std::cerr << "Failed to start listening on: " << servspec << std::endl;
    }

    freeaddrinfo(res);

    return sock;
}

bool isclosed(int sock) {
    fd_set rfd;
    FD_ZERO(&rfd);
    FD_SET(sock, &rfd);
    timeval tv = {0};
    select(sock + 1, &rfd, 0, 0, &tv);
    if (!FD_ISSET(sock, &rfd))
        return false;
    int n = 0;
    ioctl(sock, FIONREAD, &n);
    return n == 0;
}

void new_connection(Args args) {
    ssize_t r;
    char buffer[256];

    bzero(buffer, 256);

    int n = read(args.port, buffer, 255);

    if (n < 0) {
        std::cout << "Error reading from socket:" << args.port << std::endl;
        return;
    }

    std::string inputStr = buffer;
    std::cout << "Received: " << inputStr << " and " << n << std::endl;

    string outputStr;
    auto sensMap = args.uuv->m_sensors_map;

    if (strcmp(buffer, "###") == 0) {
        outputStr = "###\n";
        std::cout << "Received shutdown request" << std::endl;
        serverRunning = false;
    } else if (strcmp(buffer, "ACQ") == 0) {
        outputStr = "ACQ\n";
    } else if (strcmp(buffer, "SENSORS") == 0) {
        outputStr.clear();
        for (auto &it : sensMap) {

//				outputStr += it->first +"="+ doubleToString(it->second.averageRate,2) +",";

            //if it's not the dummy element that resembles the speed in sensors map
            if (it.first.find("SPEED") == string::npos) {
                outputStr += it.second.getSummary() + ",";
            }
            //reset sensors information
            it.second.reset();
        }

        if (outputStr.length() > 0) {
            outputStr.replace(outputStr.length() - 1, 1, "\n");
        }
    } else if (inputStr.find("SPEED") != string::npos) {
        //input string is in the form "SPEED=3.6,SENSOR1=-1,SENSOR2=0,..."
        char *dup = strdup(inputStr.c_str());
        char *token = strtok(dup, ",");
        vector<string> uuvElements;

        while (token != nullptr) {
            uuvElements.emplace_back(token);
            // the call is treated as a subsequent calls to strtok:
            // the function continues from where it left in previous invocation
            token = strtok(nullptr, ",");
        }
        free(dup);

        //iterate over tokens and extract the desired values from each token
        for (const string &str : uuvElements) {
            char *dup2 = strdup(str.c_str());
            char *token2 = strtok(dup2, "=");
            vector<string> v;
            while (token2 != nullptr) {
                v.emplace_back(token2);
                // the call is treated as a subsequent calls to strtok:
                // the function continues from where it left in previous invocation
                token2 = strtok(nullptr, ",");
            }
            free(dup2);
            if (v.size() == 2) {
                if (v.at(0).find("SPEED") != string::npos) {//SPEED=3.22
                    auto it = sensMap.find(v.at(0));
                    if (it != sensMap.end()) {
                        it->second.other = stod(v.at(1));
                    }
                } else if (v.at(0).find("SENSOR") != string::npos) {
                    auto it = sensMap.find(v.at(0));
                    if (it != sensMap.end()) {
                        it->second.state = stod(v.at(1));
                    }
                }
            }
        }

        outputStr = "OK\n";
    } else if (inputStr.rfind("FWD", 0) == 0) {
        std::cout << "Forwarding message: " << inputStr << std::endl;

        inputStr = inputStr.substr(4);

        std::istringstream iss(inputStr);
        std::string key;
        std::getline(iss, key, ':');

        std::string value;
        std::getline(iss, value, ':');

        args.uuv->ForwardMessage(key, value);
    } else {
        outputStr = "Unknown command: " + inputStr + "\n";
    }

    std::cout << "Sending: " << outputStr << std::endl;

    send(args.port, outputStr.c_str(), strlen(outputStr.c_str()), 0);

    close(args.port);
}

const char *prependPort(int port) {
    std::stringstream stream;
    stream << port;
    std::string str = stream.str().insert(0, ":");

    return str.c_str();
}

void init_outbound(UUV uuv) {
    // TODO: Initialise outbound server
}

void run_server(UUV uuv) {
    signal(SIGPIPE, SIG_IGN);

    const char *port = prependPort(9080);
    std::cout << "Initialising server on port: " << port << std::endl;

    int sock = make_accept_sock(port);

    std::cout << "Server listening on port: " << port << std::endl;

    int requests = 0;

    while (serverRunning) {
        int new_sock = accept(sock, nullptr, nullptr);

        requests++;
        std::cout << "Total requests: " << requests << std::endl;

        Args args{
                &uuv,
                new_sock
        };

        std::thread t(new_connection, args);
        t.detach();
    }
}

