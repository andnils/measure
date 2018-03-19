FROM openjdk:8-jdk-alpine

ADD target/uberjar/measure-0.1.0-SNAPSHOT-standalone.jar /srv/app.jar
ADD dist/* /dist/

EXPOSE 3000

CMD ["java", "-jar", "/srv/app.jar"]
