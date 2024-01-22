#!/bin/bash
gradle build
sesac_tomcat_path=/usr/local/tomcat
docker cp ./build/libs/hacking02_sk-0.0.1-SNAPSHOT-plain.war ${webwas_container_name}:${sesac_tomcat_path}/webapps/ROOT.war
