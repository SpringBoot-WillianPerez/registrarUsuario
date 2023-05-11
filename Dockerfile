# Imagen que contiene el jdk que usamos en nuestro proyecto
FROM eclipse-temurin:17-jre-jammy

# FROM openjdk:8-jdk-alpine
# Nuestro proyecto compilado: registrarUsuario-1.0.jar
ARG JAR_FILE=target/*.jar

#Renombramos nuestro *.jar
COPY ${JAR_FILE} app.jar

# LLevarlos al servidor y ejecutarlo. Esto se ejecuta dentro del contenedor
ENTRYPOINT ["java", "-jar", "/app.jar"]

#Esto cuando NO TENGO UNA BBDD. Si tengo BBDD ir a docker-compose.yml
#docker build -t springbootapp:1.0 .    -> Me crea la imagen. Importante estar en el dir del fich Dockerfile
#docker image list
#docker run --name registrarUsuario -p 8080:8080 -d springbootapp:1.0
