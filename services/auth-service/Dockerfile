# Build stage
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy the root pom.xml first
COPY pom.xml ./

# Copy common libraries
COPY libs/ ./libs/

# Copy the auth-service
COPY services/auth-service/ ./services/auth-service/

# First install just the parent pom
RUN mvn -N install

# Then install the common modules
RUN mvn -pl libs/common-lib,libs/common-spring clean install -DskipTests

# Now build the auth-service
WORKDIR /app/services/auth-service
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-slim
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/services/auth-service/target/auth-service-1.0.0.jar app.jar

# Add wait-for-it script
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 4006
CMD ["/bin/sh", "-c", "/wait-for-it.sh db:3306 -t 60 -- java -jar app.jar"]