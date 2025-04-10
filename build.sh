#!/bin/bash

echo "🚀 Starting Maven build, skipping product-service and tests..."

mvn clean install -pl '!product-service,!api-throttling' -DskipTests

BUILD_STATUS=$?

if [ $BUILD_STATUS -eq 0 ]; then
  echo "✅ Build successful!"
else
  echo "❌ Build failed!"
fi
