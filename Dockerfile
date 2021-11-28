FROM ubuntu:20.04

WORKDIR /app
COPY . /app
EXPOSE 8085

RUN apt-get update
RUN apt-get -y install openjdk-8-jdk
RUN apt-get -y install maven
RUN mvn install -DskipTests

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} smart.jar