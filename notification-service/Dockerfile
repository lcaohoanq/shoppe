# Build stage
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy the entire project
COPY . .

# First install just the parent pom
RUN mvn -N install

# Then install the common modules using Maven's reactor
RUN mvn -pl libs/common-lib,libs/common-spring clean install -DskipTests

# Now build the notification-service
WORKDIR /app/notification-service
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-slim
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/notification-service/target/notification-service-1.0.0.jar app.jar

# Add wait-for-it script to ensure database is ready
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 4005
# Wait for MySQL to be ready before starting the application
CMD ["/bin/sh", "-c", "/wait-for-it.sh db:3306 -t 60 -- java -jar app.jar"]