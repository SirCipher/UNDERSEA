grammar Sensors;
import UUV_Terminals;

@parser::header {
  package undersea.uuv.dsl.gen;
  import java.util.*;
}

model:
    sensor+
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

change:
		CHANGE	ASSIGN begin=(INT | DOUBLE) ':' end=(INT | DOUBLE) ':' value=(INT | DOUBLE)
;