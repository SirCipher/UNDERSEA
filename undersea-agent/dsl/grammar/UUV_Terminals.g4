lexer grammar UUV_Terminals; // note "lexer grammar"

@lexer::header {
package com.type2labs.undersea.dsl.uuv.gen;
} 

ASSIGN: 
	'='
;

ACTIVE:
    'active'
;

BOOL:
    'false' | 'true'
;

MISSION_NAME:
    'mission name'
;

NAME:
	'name'
;

TIME_WINDOW:
	'time window'
;

SERVER_HOST:
	'host'
;

SENSORS:
    'sensors'
;

DOUBLE:
	INT? '.' INT
;

PORT_START:
    'server port start'
;

SENSOR_PORT:
    'sensor port'
;

SIMULATION_TIME:
	'simulation time'
;

SIMULATION_SPEED:
	'simulation speed'
;

INT: 
	[0-9]+
;

IP:
	OCTET '.' OCTET '.' OCTET '.' OCTET
;

fragment DIGIT :
	'0'..'9'
;

OCTET
	 :  DIGIT DIGIT DIGIT
	 |  DIGIT DIGIT
     |  DIGIT
;

BEGL:
    '['
;

ENDL:
    ']'
;

SEP:
    ','
;

SPEED:
	'speed'
;

ID:
	[a-zA-Z_][0-9a-zA-Z_]+
;

WS  :
	[ \t\r\n]+ -> skip
;


ErrorCharacter : . ;