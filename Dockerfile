FROM openjdk:11
ARG JAR_FILE=target/movie-chooser.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
