FROM eclipse-temurin:20-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 3015
ENTRYPOINT ["java","-jar","/app.jar"]
