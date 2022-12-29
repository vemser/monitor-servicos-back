FROM openjdk:18-jdk-slim
RUN apt-get update; apt-get install -y fontconfig libfreetype6
COPY /target/*.jar /usr/local/lib/app.jar
EXPOSE 8080
ENV JAVA_OPTS="{{javaOpts}}"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /usr/local/lib/app.jar" ]