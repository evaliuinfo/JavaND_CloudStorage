#!/bin/bash
echo "Usage: ./create_cluster.sh <aws region>"

if [ -z $1 ]; then
    Region="--region us-west-2"
else
    Region="--region $1"
fi

echo "Create ECS Fargate Cluster"
ecs-cli configure ${Region} --cluster cloud-storage --default-launch-type FARGATE --config-name cloud-storage 
ecs-cli up --cluster-config cloud-storage --ecs-profile cloud-profile > out.log

VpcId=`cat out.log | grep "VPC" | awk -F ' ' '{print $3}'`
SubnetId1=`cat out.log| grep "Subnet" | awk -F ' ' '{print $3}' | sed -n 1p`
SubnetId2=`cat out.log| grep "Subnet" | awk -F ' ' '{print $3}' | sed -n 2p`
rm out.log

echo "Update Security Group"
aws ec2 ${Region} describe-security-groups --filters Name=vpc-id,Values=${VpcId} > sg.log
SecurityGroupId=`cat sg.log | jq '(.SecurityGroups[] | select(.GroupName=="default")) | .GroupId' | tr -d '"'`
aws ec2 ${Region} authorize-security-group-ingress --group-id ${SecurityGroupId} --protocol tcp --port 8080 --cidr 0.0.0.0/0
rm sg.log
