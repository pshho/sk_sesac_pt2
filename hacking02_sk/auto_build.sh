#!/bin/bash
webwas_container_name=$1

if [ -z "$webwas_containername" ]; then
    echo "오류: 도커 컨테이너 이름을 지정해주세요."
    echo "사용법: $0 <도커컨테이너_이름>"
    exit 1
fi

mvn package
sesac_tomcat_path=/usr/local/tomcat
docker cp ./target/stu.war ${webwas_container_name}:${sesac_tomcat_path}/webapps/ROOT.war