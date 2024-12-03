# Usa una imagen base de JDK 17 (puedes cambiar a JDK 11 si es necesario)
FROM openjdk:17-jdk-slim

# Define el archivo JAR a copiar
ARG JAR_FILE=target/*.jar

# Copia el archivo JAR en el contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto 8080 para la aplicación Spring Boot
EXPOSE 8080

# Define el comando de inicio para la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]

