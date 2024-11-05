# Etapa de construcción
FROM maven:eclipse-temurin AS build

# Copia el código fuente y el archivo de configuración de Maven
WORKDIR /build
COPY pom.xml .
COPY src ./src

# Ejecuta la compilación para generar el archivo JAR
RUN mvn clean package

# Etapa final: JRE para ejecutar la aplicación
FROM eclipse-temurin:21-jre

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado en la etapa de construcción al directorio de trabajo
COPY --from=build /build/target/tomi-mqtt-broker-1.0-SNAPSHOT.jar /app/app.jar

# Expone el puerto 80 para MQTT
EXPOSE 1883

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
