# Deployment Runbook for Cloud Storage

SuperDuperDrive Cloud Storage is an online web application supports user to upload/download/remove files via web interface, also has features to manage user personal information like credentials and notes. 

Application can be tested via localhost:8080 once it is fully deployed.

This deployment runbook contains all the details for deploying Cloud Storage via Docker image.

## Content

1. [Pre-requisites](#pre-requisites)
2. [Deployment Process](#deployment-process)
3. [Deployment via AWS ECR](#deployment-via-aws-ecr)


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

Or can pull the image from dockerhub:
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

TBD

