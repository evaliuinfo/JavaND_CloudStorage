#!/bin/bash
echo "Usage ./create_ecr_repo.sh <aws profile> <aws region>"

if [ -z $1 ]; then
    echo "Please specify AWS profile"
    Profile=""
    exit 1;
else
    Profile="--profile $1"
fi

if [ -z $2 ]; then
    echo "Use default region us-west-2"
    Region="--region us-west-2"
else
    Region="--region $2"
fi

echo "Docker Login with AWS ECR"
Password=`aws ecr get-login-password ${Region}`
docker login -u AWS -p ${Password}

echo "Create AWS ECR Repository"
aws ecr ${Profile} ${Region} create-repository --repository-name cloud-storage-repo

echo "Build Images"
cd ../
AccountId=`aws sts get-caller-identity ${Profile} ${Region} | jq .Account | tr -d '"'`
docker build --tag=${AccountId}.dkr.ecr.${2}.amazonaws.com/cloud-storage-repo .
docker push ${AccountId}.dkr.ecr.${2}.amazonaws.com/cloud-storage-repo
cd deployment/
