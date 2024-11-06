#Start with a base image containing java runtime
FROM openjdk:17-jdk-slim

#Informtion around who maintains the image
MAINTAINER ikucuk.com

#add the application's jar to the image
COPY target/cards-0.0.1-SNAPSHOT.jar cards-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/cards-0.0.1-SNAPSHOT.jar"]