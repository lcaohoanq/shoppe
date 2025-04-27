# Build stage
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy only the pom.xml and download dependencies (caching this layer)
COPY pom.xml ./
COPY src ./src/
RUN mvn dependency:go-offline

# Now copy the rest of the source code and build
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-slim
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/keycloak-service-1.0.0.jar app.jar

# Add wait-for-it script to ensure database is ready
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Create entrypoint script to read secrets
RUN echo '#!/bin/sh\n\
if [ -f "$DB_PASSWORD_FILE" ]; then\n\
  export DB_PASSWORD=$(cat $DB_PASSWORD_FILE)\n\
fi\n\
/wait-for-it.sh db:5432 -t 60 -- java -jar app.jar' > /entrypoint.sh && \
chmod +x /entrypoint.sh

EXPOSE 9080

ENTRYPOINT ["/entrypoint.sh"]
