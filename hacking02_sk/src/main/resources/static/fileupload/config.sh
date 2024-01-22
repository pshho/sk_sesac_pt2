#!/usr/bin/env bash

# vim configuration 
echo 'alias vi=vim' >> /etc/profile

# swapoff -a to disable swapping
swapoff -a
# sed to comment the swap partition in /etc/fstab
sed -i.bak -r 's/(.+ swap .+)/#\1/' /etc/fstab

# kubernetes repo
gg_pkg="packages.cloud.google.com/yum/doc" # Due to shorten addr for key
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://packages.cloud.google.com/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=https://${gg_pkg}/yum-key.gpg https://${gg_pkg}/rpm-package-key.gpg
EOF

# Set SELinux in permissive mode (effectively disabling it)
setenforce 0
sed -i 's/^SELINUX=enforcing$/SELINUX=permissive/' /etc/selinux/config

# RHEL/CentOS 7 have reported traffic issues being routed incorrectly due to iptables bypassed
cat <<EOF >  /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF
modprobe br_netfilter

# local small dns & vagrant cannot parse and delivery shell code.

echo "마스터노드 ip주소 입력 : "
read MASTER_NODE_IP

echo "마스터노드 호스트 이름 입력 : "
read MASTER_NODE_HOSTNAME

if [ ! -z $MASTER_NODE_IP ] && [ ! -z $MASTER_NODE_HOSTNAME ];then
	echo "$MASTER_NODE_IP $MASTER_NODE_HOSTNAME"

else
	echo "192.168.1.10 m-k8s" >> /etc/hosts
fi



for (( i=1; i<=$1; i++  )); do 

	echo "워커노드 ip주소 입력 : "
	read WORKER_NODE_IP

	echo "워커노드 호스트 이름 입력 : "
	read WORKER_NODE_HOSTNAME


	if [ ! -z $WORKER_NODE_IP ] && [ ! -z $WORKER_NODE_HOSTNAME ];then
		echo "${WORKER_NODE_IP} ${WORKER_NODE_HOSTNAME}" >> /etc/hosts; 
	else
		echo "192.168.1.10$i w$i-k8s" >> /etc/hosts; 
	fi
done

# config DNS  
cat <<EOF > /etc/resolv.conf
nameserver 1.1.1.1 #cloudflare DNS
nameserver 8.8.8.8 #Google DNS
EOF

