# Utilizamos OpenJDK 21 con versión slim
FROM openjdk:21-slim

# Instalamos dependencias necesarias
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    build-essential \
    gcc \
    make \
    libcjson-dev && \
    rm -rf /var/lib/apt/lists/*

# Creamos directorio de trabajo
WORKDIR /app

# Copiamos el código fuente
COPY . .

# Compilamos el código nativo
RUN cd src/main/native && \
    make clean && \
    make && \
    cp libanalisisnumerico.so /usr/local/lib/ && \
    chmod 755 /usr/local/lib/libanalisisnumerico.so

# Compilamos la aplicación Java
RUN ./mvnw clean package -DskipTests

# Variables de entorno
ENV LD_LIBRARY_PATH=/usr/local/lib:$LD_LIBRARY_PATH
ENV JAVA_OPTS="-Djava.library.path=/usr/local/lib"

# Puerto expuesto
EXPOSE 8080

# Archivo JAR de la aplicación
ARG JAR_FILE=target/analisis-jni-0.0.1-SNAPSHOT.jar

# Copiamos el JAR
COPY ${JAR_FILE} app.jar

# Punto de entrada
ENTRYPOINT ["java", "-jar", "/app.jar"]