FROM openjdk:21
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/Analisis-jni-*.jar
ADD ${JAR_FILE} app.jar
COPY libanalisisnumerico.so /usr/local/lib/
ENV LD_LIBRARY_PATH=/usr/local/lib:$LD_LIBRARY_PATH
RUN chmod +x /usr/local/lib/libanalisisnumerico.so
ENTRYPOINT ["java", "-jar", "/app.jar"]