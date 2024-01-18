#!/bin/bash
gradle build
sudo cp -f ./build/libs/hacking02_sk-0.0.1-SNAPSHOT.war /usr/local/tomcat/apache-tomcat-8.0.53/webapps/ROOT.war
