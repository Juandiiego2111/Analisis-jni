FROM openjdk:21-slim
RUN apt-get update && apt-get install -y libcjson1
COPY target/analisis-jni-0.0.1-SNAPSHOT.jar app.jar
COPY libanalisisnumerico.so /usr/local/lib/
RUN chmod +x /usr/local/lib/libanalisisnumerico.so
ENTRYPOINT ["java", "-Djava.library.path=/usr/local/lib", "-jar", "app.jar"]