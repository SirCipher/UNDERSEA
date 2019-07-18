grammar UUV;

import UUV_Terminals; // includes all rules from UUV_Terminals.g4

@parser::header {
package undersea.uuv.dsl.gen;
import java.util.*;
}

@parser::members {
	Set<String> types = new HashSet<String>() {{add("T");}};
	boolean istype() { return types.contains(getCurrentToken().getText()); }
}

/** The start rule; begin parsing here.
 * a model is assembled by a model type and a set of abstract elements*/

model:
		( portStart
		| simulation
		| invocation
		| host
	 	| uuv
	 	| speed)+
;

portStart:
    PORT_START ASSIGN value=INT
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

speed:
		SIMULATION_SPEED ASSIGN value=INT
;

list:
    BEGL elems? ENDL
;

elems:
    elem ( SEP elem )*
;

elem:
    ID
;

uuv:
		'UUV'
		'{'
			(
				NAME ASSIGN name=ID
			|	SPEED ASSIGN min=(INT | DOUBLE) ':' max=(INT | DOUBLE) ':' steps=INT
            |   BEHAVIOUR_FILE ASSIGN behaviourFile=BHV_FILE
			|   SENSORS ASSIGN sensors=BEGL elems? ENDL
			)+
		'}'
;
