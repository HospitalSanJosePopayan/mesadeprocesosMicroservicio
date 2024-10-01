#Dockerfile para la creación de la imagen de la aplicación
#utilizando la imagen base de Java 21
#Se copia el archivo jar de la aplicación a la imagen
#y ejecuta el comando para correr la aplicación
FROM openjdk:21
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
# Fin del archivo Dockerfile