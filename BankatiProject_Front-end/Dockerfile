# Étape de construction du frontend Angular
FROM node:16 AS build-frontend
WORKDIR /app
COPY frontend/ .
RUN npm install
RUN npm run build --prod

# Étape de construction du backend Spring Boot
FROM maven:3.8.5-openjdk-17 AS build-backend
WORKDIR /app
COPY backend/ .
RUN mvn clean package -DskipTests

# Étape finale de l'image combinée
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app

# Copier le backend JAR
COPY --from=build-backend /app/target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Copier les fichiers statiques du frontend
COPY --from=build-frontend /app/dist/ /app/static/

# Exposer le port 8080
EXPOSE 8080

# Définir le point d'entrée
ENTRYPOINT ["java", "-jar", "demo.jar"]
