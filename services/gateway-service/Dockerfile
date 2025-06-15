# Build stage
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy the root pom.xml first
COPY pom.xml ./

# Copy common libraries if gateway needs them
COPY libs/ ./libs/

# Copy the gateway-service
COPY services/gateway-service/ ./services/gateway-service/

# First install just the parent pom
RUN mvn -N install

# Then install the common modules (if needed)
RUN mvn -pl libs/common-lib,libs/common-spring clean install -DskipTests

# Now build the gateway-service
WORKDIR /app/services/gateway-service
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-slim
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/services/gateway-service/target/gateway-service-*.jar app.jar

# Add wait-for-it script to ensure database is ready
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 4003
# Wait for MySQL to be ready before starting the application
CMD ["java", "-jar", "app.jar"]
