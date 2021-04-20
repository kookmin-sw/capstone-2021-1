FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
EXPOSE 8080
ADD ${JAR_FILE} playmaker.jar
ENTRYPOINT ["java","-jar","/playmaker.jar"]
