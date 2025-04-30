#!/usr/bin/env bash

KEYCLOAK_VERSION="26.1.3"
POSTGRES_VERSION="17.2"

source scripts/my-functions.sh

echo
echo "Starting environment"
echo "===================="

echo
echo "Creating network"
echo "----------------"
docker network create springboot-react-keycloak-net

# Remove the postgres-keycloak container section since we'll use Supabase instead

echo
echo "Starting postgres for App"
echo "--------------------------"

docker run -d \
    --name postgres-app \
    -p 5433:5432 \
    -e POSTGRES_DB=app_db \
    -e POSTGRES_USER=app_user \
    -e POSTGRES_PASSWORD=app_password \
    --network=springboot-react-keycloak-net \
    postgres:${POSTGRES_VERSION}

echo
echo "Starting keycloak with Supabase"
echo "-------------------------------"

# Load environment variables from .env file
if [ -f .env ]; then
  # shellcheck disable=SC2046
  export $(grep -v '^#' .env | xargs)
fi

docker run -d \
    --name keycloak \
    -p 8080:8080 \
    -e KC_BOOTSTRAP_ADMIN_USERNAME=admin \
    -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin \
    -e KC_DB=postgres \
    -e KC_DB_URL_HOST=${SUPABASE_HOST} \
    -e KC_DB_URL="jdbc:postgresql://${SUPABASE_HOST}:${SUPABASE_PORT}/${SUPABASE_DATABASE}" \
    -e KC_DB_USERNAME="${SUPABASE_USER}" \
    -e KC_DB_PASSWORD="${SUPABASE_PASSWORD}" \
    -e KC_DB_SCHEMA=public \
    --network=springboot-react-keycloak-net \
    quay.io/keycloak/keycloak:${KEYCLOAK_VERSION} start-dev

echo
wait_for_container_log "postgres-app" "database system is ready"
echo
wait_for_container_log "keycloak" "started in"

echo
echo "Environment Up and Running"
echo "=========================="
echo
