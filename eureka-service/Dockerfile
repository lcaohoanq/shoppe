# Build stage for parent dependencies
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build-parent
WORKDIR /app

# Copy the parent POM into the container
COPY ./pom.xml ./

# Download all dependencies for the parent project
RUN mvn dependency:go-offline

# Build the eureka-service
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy parent dependencies from the build-parent stage
COPY --from=build-parent /app/.m2 /root/.m2

# Copy eureka-service specific files
COPY ./eureka-service/pom.xml ./eureka-service/
COPY ./eureka-service/src ./eureka-service/src/

# Build the eureka-service jar
RUN mvn clean package -DskipTests -f ./eureka-service/pom.xml

# Runtime stage
FROM openjdk:17-slim
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/eureka-service/target/eureka-service-1.0.0.jar app.jar

# Add wait-for-it script to ensure database is ready
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8080
# Wait for MySQL to be ready before starting the application
CMD ["/bin/sh", "-c", "/wait-for-it.sh db:3306 -t 60 -- java -jar app.jar"]
