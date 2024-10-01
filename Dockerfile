# Usa una imagen base oficial de Python
FROM python:3.9-slim

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el contenido actual del directorio al contenedor
COPY . .

# Comando por defecto para ejecutar el contenedor
CMD ["python", "-c", "print('Hola Mundo')"]
