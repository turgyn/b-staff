# Stage 1: Build the application
FROM maven:3.8.1-openjdk-17 as build

# Set the current working directory in the Docker image
WORKDIR /app

# Copy the pom.xml file into the Docker image
COPY pom.xml .

# Download all required dependencies into the Docker image
RUN mvn dependency:go-offline

# Copy your source code into the Docker image
COPY src src

# Build the application
RUN mvn package

# Stage 2: Run the application
FROM eclipse-temurin:17

# Add Maintainer Info
LABEL maintainer="email@example.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Copy the application's jar file from the build stage into this stage
COPY --from=build /app/target/komek-backend-0.0.1.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
