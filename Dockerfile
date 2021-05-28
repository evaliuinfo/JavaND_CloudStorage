# Select parent image
FROM maven:3.6.3-jdk-8

COPY ./ ./

# Package the application code
RUN mvn clean package

# Set the startup command
CMD ["java", "-jar", "target/cloudstorage-0.0.1-SNAPSHOT.jar"]
