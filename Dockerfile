FROM openjdk:8-jdk-alpine
# TODO: copy html/js files
# TODO: add postgres10
# TODO: add nginx
ADD target/measure-0.1.0-SNAPSHOT-standalone.jar /srv/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/srv/app.jar"]
