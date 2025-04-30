#!/usr/bin/env bash

echo
echo "Starting the environment shutdown"
echo "================================="

echo
echo "Removing containers"
echo "-------------------"
docker rm -fv keycloak postgres-keycloak postgres-app

echo
echo "Removing network"
echo "----------------"
docker network rm springboot-react-keycloak-net

echo
echo "Environment shutdown successfully"
echo "================================="
echo
