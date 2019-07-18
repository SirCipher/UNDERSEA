lexer grammar UUV_Terminals; // note "lexer grammar"

@lexer::header {
  package undersea.uuv.dsl.gen;
} 

ASSIGN: 
	'=' ;

SERVER_HOST: 
	'host'
;

SENSORS:
    'sensors'
;

BEHAVIOUR_FILE:
    'behaviour file'
;

BHV_FILE:
    ID '.bhv'
;

PORT_START:
    'serverPort start'
;

SIMULATION_TIME: 
	'simulation time'
;

TIME_WINDOW: 
	'time window'
;

SIMULATION_SPEED:
	'simulation speed'
;

SPEED:
	'speed'
;

SENSOR:
    'sensor'
;

NAME:
	'name'
;

RATE:
	'rate'
;

CHANGE:
	'change'
;

RELIABILITY:
	'reliability'
;

SLCOMMENT: 
	'//' .*? '\r'? '\n' -> skip // Match "//" stuff '\n'
;

ID: 
	[a-zA-Z_][0-9a-zA-Z_]* 
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

INT: 
	[0-9]+ 
;  

DOUBLE:
	INT? '.' INT 
;

IP: 
	OCTET '.' OCTET '.' OCTET '.' OCTET
;

OCTET
	 :  DIGIT DIGIT DIGIT
	 |  DIGIT DIGIT
     |  DIGIT
 ;

STRING
    :  '"' (~('\\'|'"') )* '"'
;

WS  : 
	[ \t\r\n]+ -> skip /*toss out whitespace*/
;

fragment DIGIT :  
	'0'..'9'
;