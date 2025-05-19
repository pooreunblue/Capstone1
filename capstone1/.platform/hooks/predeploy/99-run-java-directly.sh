#!/bin/bash
pkill -f 'java -jar'
sleep 2
nohup java -Djava.net.preferIPv4Stack=true -jar /var/app/current/application.jar > /var/log/udp-app.log 2>&1 &
