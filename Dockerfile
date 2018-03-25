FROM openjdk:8-jdk-alpine
COPY target/uberjar/measure-0.1.0-SNAPSHOT-standalone.jar /srv/measure.jar

EXPOSE 3000
