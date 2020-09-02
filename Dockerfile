FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/numbers-1.0.0-PART2.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]