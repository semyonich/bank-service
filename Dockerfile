FROM openjdk:11-oracle
MAINTAINER Serhii Semenykhin
COPY target/bank-service-0.0.1-SNAPSHOT.jar bank-service-0.0.1.jar
ENTRYPOINT ["java","-jar","/bank-service-0.0.1.jar"]
