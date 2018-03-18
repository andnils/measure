FROM openjdk:8-jdk-alpine
# TODO: copy html/js files
# TODO: add postgres10
# TODO: add nginx
# TODO: set environment vars (MEASURE_HTTP_PORT, MEASURE_DB_URL, MEASURE_DB_USER, MEASURE_DB_PASSWORD)
ADD target/uberjar/measure-0.1.0-SNAPSHOT-standalone.jar /srv/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/srv/app.jar"]
