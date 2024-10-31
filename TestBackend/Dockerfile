# Use a base image with JDK
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

RUN apt-get update && apt-get install -y \
    maven

# Copy the project files into the container
COPY . .

# Run Maven build to create the JAR file
RUN mvn clean install

# Copy the application's JAR file
COPY target/TestBackend-0.0.1-SNAPSHOT.jar app.jar


# Expose the port that the application will run on
EXPOSE 8090

# Run the JAR file
CMD ["java", "-jar", "app.jar"]
