# Build stage
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy the root pom.xml first
COPY pom.xml ./

# Copy common libraries if eureka needs them
COPY libs/ ./libs/

# Copy the eureka-service
COPY services/eureka-service/ ./services/eureka-service/

# First install just the parent pom
RUN mvn -N install

# Then install the common modules (if needed)
RUN mvn -pl libs/common-lib,libs/common-spring clean install -DskipTests

# Now build the eureka-service
WORKDIR /app/services/eureka-service
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-slim
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/services/eureka-service/target/eureka-service-*.jar app.jar

EXPOSE 8761
CMD ["java", "-jar", "app.jar"]