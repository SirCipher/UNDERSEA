lexer grammar UUV_Terminals; // note "lexer grammar"

@lexer::header {
  package uuv.dsl.gen;
} 

ASSIGN: 
	'=' ;

SERVER_HOST: 
	'host'
;

SERVER_PORT: 
	'port'
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