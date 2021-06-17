# Deployment Runbook for Cloud Storage

SuperDuperDrive Cloud Storage is an online web application supports user to upload/download/remove files via web interface, also has features to manage user personal information like credentials and notes. 

Application can be tested via localhost:8080 once it is fully deployed.

This deployment runbook contains all the details for deploying Cloud Storage via Docker image.

## Content

1. [Pre-requisites](#1-pre-requisites)
2. [Deployment Process](#2-deployment-process)
3. [Deployment via AWS ECR](#3-deployment-via-aws-ecr)


## 1. Pre-requisites

- Docker is intalled on the local machine
- Maven 3.8.1+ is installed
- Java 11+ is intalled

## 2. Deployment Process

### 2.1 Create Docker Image

First, create the Docker image based on current repository:
```
docker build -t localrepo/docker-build-cloudstorage:1.0-SNAPSHOT .
```

Or pull the image from dockerhub:
```
docker pull evaliuinfo/docker-normal-build-cloudstorage
```

### 2.2 Run the Application

Once the image is ready, run the application with port 8080:
```
docker run -d --name cloudstorage -p 8080:8080 localrepo/docker-build-cloudstorage:1.0-SNAPSHOT 
```

To test the application, please navigate to http://localhost:8080 


### 2.3 Stop the Application

Command to stop the running application: 
```
docker stop cloudstorage
```

## 3. Deployment via AWS ECR

### 3.1 Prepare ECR Repository

```
cd deployment/
./create_ecr_repo.sh <aws profile> <aws region>
```

### 3.2 Set up ECS IAM Roles

```
./create_ecs_task_role.sh <aws profile> <aws region>
```

### 3.3 Configure ECS

First need to configure ECS CLI profile using AWS access key and secret key:
```
ecs-cli configure profile --access-key AWS_ACCESS_KEY_ID --secret-key AWS_SECRET_ACCESS_KEY --profile-name cloud-profile
```

Prepare ECS Cluster:
```
./create_cluster.sh <aws region>
```

### 3.4 Deploy Cloud Storage to ECS Cluster

Make sure update the docker-compose.yml file according to your images on ECR:
```yaml
version: '3'
services:
  web:
    image: {AWS_Account_Id}.dkr.ecr.us-west-2.amazonaws.com/cloud-storage-repo
    ports:
      - "8080:8080"
    logging:
      driver: awslogs
      options:
        awslogs-group: cloud-storage-aws-ecs
        awslogs-region: us-west-2
        awslogs-stream-prefix: web
```

Update the ecs-params.yml file based on VPC subnets and Security Group created in step 3.3:
```yaml
version: 1
task_definition:
  task_execution_role: ecsTaskExecutionRole
  ecs_network_mode: awsvpc
  task_size:
    mem_limit: 0.5GB
    cpu_limit: 256
run_params:
  network_configuration:
    awsvpc_configuration:
      subnets:
        - {Public_Subnet1_ID}
        - {Public_Subnet2_ID}
      security_groups:
        - {Security_Group_ID}
      assign_public_ip: ENABLED
```

Then, deploy to ECS Cluster:
```bash
ecs-cli compose --project-name cloud-storage service up --create-log-groups --cluster-config cloud-storage --ecs-profile cloud-profile
```

Then get the service web IP from cli:
```bash
ecs-cli compose --project-name cloud-storage service ps --cluster-config cloud-storage --ecs-profile cloud-profile
```

Service can be tested by visit: http://{Public_IP_From_Previous_Step}:8080/
