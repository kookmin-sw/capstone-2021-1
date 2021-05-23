FROM openjdk:11-jdk
ARG JAR_FILE=target/*.jar
EXPOSE 8080
ADD ${JAR_FILE} playmaker.jar
ENTRYPOINT ["java","-jar","/playmaker.jar"]
