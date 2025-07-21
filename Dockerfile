# docker build -t blk-hacking-mx-gael-alburo .
# Imagen basada en Alpine Linux por ser ligera, segura y optimizada para producción
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia el JAR generado al contenedor
COPY target/*.jar app.jar

# Expone el puerto interno 80
EXPOSE 80

# Comando para ejecutar la app en el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]