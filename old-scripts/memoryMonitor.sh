#!/bin/bash

CONTROLLER=UNDERSEA_Controller

sleep 2

ID=`ps -ef | grep $CONTROLLER| grep -v grep | awk '{print $2}'`
echo $ID

#visualvm --openpid $ID &
jvisualvm --openpid $ID &

