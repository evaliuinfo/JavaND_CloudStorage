#!/bin/bash
echo "Usage: ./create_ecs_task_role.sh <aws profile> <aws region>"

if [ -z $1 ]; then
    echo "Please specify AWS Profile"
    Profile=""
else
    Profile="--profile $1"
fi

if [ -z $2 ]; then
    Region="--region us-west-2"
else
    Region="--region $2"
fi

echo "Create ECS Task Role"
aws iam ${Profile} ${Region} create-role --role-name ecsTaskExecutionRole --assume-role-policy-document file://ecs_task_role.json
aws iam ${Profile} ${Region} attach-role-policy --role-name ecsTaskExecutionRole --policy-arn arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy
