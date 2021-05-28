# Deployment Runbook for Super*Duper*Drive Cloud Storage

This is the deployment runbook for deploying Cloud Storage via Docker image.

## 1. Pre-requisites

- Docker is intalled on the local machine
- Maven 3.8.1+ is installed
- Java 11+ is intalled

## 2. Deployment Process

### 2.1 Create Docker Image

First, we need to create the Docker image based on this repo:
```
docker build -t localrepo/docker-build-cloudstorage:1.0-SNAPSHOT .
```

Or the image can be pulled from dockerhub:
```
docker pull evaliuinfo/docker-normal-build-cloudstorage
```

### 2.2 Run the Application with port 8080

We can run the application afte the image is ready with port 8080:
```
docker run -d --name cloudstorage -p 8080:8080 localrepo/docker-build-cloudstorage:1.0-SNAPSHOT 
```

To test the application, please navigate to http://localhost:8080 


### 2.3 Stop the Application

Command to stop the running application: 
```
docker stop cloudstorage
```



