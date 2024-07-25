# Usa una imagen base con Gradle y JDK
FROM gradle:8.3-jdk17 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo gradlew, build.gradle y el código fuente al contenedor
COPY gradlew .
COPY gradlew.bat .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
COPY src ./src

# Da permisos de ejecución al script gradlew
RUN chmod +x gradlew

# Construye el proyecto
RUN ./gradlew build -x test --parallel --continue --no-daemon

# Usa una imagen base de Java para ejecutar la aplicación
FROM openjdk:17-jdk-alpine

# Copia el JAR construido al nuevo contenedor
COPY --from=build /app/build/libs/Cuenta-0.0.1-SNAPSHOT.jar /microservicio-cuenta.jar

# Expone el puerto en el que la aplicación escucha
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/microservicio-cuenta.jar"]
