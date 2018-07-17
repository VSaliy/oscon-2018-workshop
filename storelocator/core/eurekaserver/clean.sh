#!/usr/bin/env bash

PID=`docker ps | grep eurekaserver | awk '{print $1}'`
echo "Stopping container $PID"
docker stop $PID

echo "Removing container $PID"
docker rm $PID