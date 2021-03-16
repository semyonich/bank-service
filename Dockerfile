FROM openjdk:11-oracle
MAINTAINER Serhii Semenykhin
COPY target/bank-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
