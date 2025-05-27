FROM openjdk:21-slim

# Instalar dependencias necesarias
RUN apt-get update && \
    apt-get install -y libcjson1 libcjson-dev && \
    rm -rf /var/lib/apt/lists/*

# Configuración de la aplicación
WORKDIR /app
VOLUME /tmp
EXPOSE 8080

# Copiar el JAR y la biblioteca nativa
ARG JAR_FILE=target/analisis-jni-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY libanalisisnumerico.so /usr/local/lib/

# Asegurar que la biblioteca sea encontrada
RUN ldconfig

# Comando de ejecución
ENTRYPOINT ["java", "-Djava.library.path=/usr/local/lib", "-jar", "app.jar"]