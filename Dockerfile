FROM openjdk:21-slim
RUN apt-get update && apt-get install -y libcjson1 libcjson-dev
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/analisis-jni-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY libanalisisnumerico.so /usr/local/lib/
ENTRYPOINT ["java", "-Djava.library.path=/usr/local/lib", "-jar", "app.jar"]