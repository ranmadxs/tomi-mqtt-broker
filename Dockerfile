# Usa una imagen base con Java 21
FROM eclipse-temurin:21-jre

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado en el directorio de trabajo
COPY target/tomi-mqtt-broker-1.0-SNAPSHOT.jar /app/app.jar

# Expone el puerto 1883 para MQTT
EXPOSE 1883

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
