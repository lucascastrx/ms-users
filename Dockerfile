FROM openjdk:17-jdk-alpine

COPY target/appname.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]