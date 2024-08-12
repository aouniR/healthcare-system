#!/bin/bash

SERVICES="api-gateway user-service authentication-service notification-service"

for service in $SERVICES; do
    echo "Building $service..."
    docker build -t $service:latest ./$service
done
