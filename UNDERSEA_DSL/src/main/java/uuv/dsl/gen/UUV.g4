grammar UUV;

import UUV_Terminals; // includes all rules from UUV_Terminals.g4

@parser::header {
  package uuv.dsl.gen;
  import java.util.*;
}

@parser::members {
	Set<String> types = new HashSet<String>() {{add("T");}};
	boolean istype() { return types.contains(getCurrentToken().getText()); }
}

/** The start rule; begin parsing here.
 * a model is assembled by a model type and a set of abstract elements*/

model:
		( simulation
		| invocation
		| host
	 	| port
	 	| uuv
	 	| speed
	 	| sensor)+
;

simulation:
		SIMULATION_TIME ASSIGN value=INT
;

invocation:
		TIME_WINDOW ASSIGN value=(INT | DOUBLE)
;

host:
		SERVER_HOST ASSIGN value=(IP | 'localhost')
;

port:
		SERVER_PORT ASSIGN value=INT
;

speed:
		SIMULATION_SPEED ASSIGN value=INT
;

sensor:
		'SENSOR'
		'{'
			(
				NAME 		ASSIGN name=ID
			|	RATE 		ASSIGN rate=(INT | DOUBLE)
			|	RELIABILITY	ASSIGN reliability=(INT | DOUBLE)
			|	change+
			)+
		'}'
;

uuv:
		'UUV'
		'{'
			(
				NAME ASSIGN name=ID
			|	SPEED ASSIGN min=(INT | DOUBLE) ':' max=(INT | DOUBLE) ':' steps=INT
			|	SERVER_PORT ASSIGN value=INT
			|   SENSOR ASSIGN sensors=sensor+
			)+
		'}'
;

change:
		CHANGE	ASSIGN begin=(INT | DOUBLE) ':' end=(INT | DOUBLE) ':' value=(INT | DOUBLE)
;