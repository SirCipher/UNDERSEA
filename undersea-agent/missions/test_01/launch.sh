#---------------------
# Launch the processes
#---------------------
printf "Launching shoreside MOOS Community"
pAntler meta_shoreside.moos >& /dev/null &


printf "Launching alpha MOOS Community"
pAntler meta_vehicle_alpha.moos >& /dev/null &

#--------------------------------------------------
# Launch uMAC and kill everything upon exiting uMAC
#--------------------------------------------------
uMAC meta_shoreside.moos
printf "Killing all processes...\n"
kill %1 %2
printf "Done killing processes...\n"
