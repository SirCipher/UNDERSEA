lexer grammar Sensors_Terminals; // note "lexer grammar"

@lexer::header {
  package undersea.uuv.dsl.gen;
}

NAME:
	'name'
;

ASSIGN:
	'='
;

CHANGE:
	'change'
;

RELIABILITY:
	'reliability'
;

RATE:
	'rate'
;

SENSORS:
    'sensors'
;

DOUBLE:
	INT? '.' INT
;

fragment DIGIT :
	'0'..'9'
;

INT:
	[0-9]+
;

ID:
	[a-zA-Z_][0-9a-zA-Z_]+
;

WS  :
	[ \t\r\n]+ -> skip
;


ErrorCharacter : . ;