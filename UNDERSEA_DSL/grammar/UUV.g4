grammar UUV;

import UUV_Terminals; // includes all rules from UUV_Terminals.g4

@parser::header {
package undersea.uuv.dsl.gen;
import java.util.*;
}

model:
		(missionName
		|portStart
        |simulation
        |invocation
        |host
        |speed
        |uuv+)+
;

missionName:
    MISSION_NAME ASSIGN value=ID
;

portStart:
    PORT_START ASSIGN value=INT
;

simulation:
    SIMULATION_TIME ASSIGN value=INT
;

speed:
    SIMULATION_SPEED ASSIGN value=INT
;

invocation:
		TIME_WINDOW ASSIGN value=(INT | DOUBLE)
;

host:
		SERVER_HOST ASSIGN value=(IP | 'localhost')
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
			NAME ASSIGN name=ID
			SPEED ASSIGN min=(INT | DOUBLE) ':' max=(INT | DOUBLE) ':' steps=INT
            BEHAVIOUR_FILE ASSIGN behaviourFile=BHV_FILE
			SENSORS ASSIGN sensors=BEGL elems? ENDL
		'}'
;
