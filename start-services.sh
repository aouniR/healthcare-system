#!/bin/bash

# Configuration
SERVICES="eureka-server api-gateway config-server authentication-service user-service notification-service"
PID_FILE="pids.txt"
DOCKER_COMPOSE_FILE="docker-compose-db.yml"

# Function to start Docker Compose
start_docker_containers() {
    echo "Starting Docker containers..."
    docker-compose -f $DOCKER_COMPOSE_FILE up -d
    if [ $? -ne 0 ]; then
        echo "Failed to start Docker containers. Exiting."
        exit 1
    fi
}

# Function to check if Docker containers are ready
wait_for_docker_containers() {
    echo "Waiting for Docker containers to be ready..."
    sleep 10  # Initial wait to give containers time to start

    while true; do
        # Check if the containers are up
        CONTAINER_STATUS=$(docker inspect -f '{{.State.Running}}' $(docker ps -q))
        if [[ $CONTAINER_STATUS == *"true"* ]]; then
            echo "All Docker containers are up and running."
            break
        else
            echo "Docker containers are not ready yet. Waiting..."
            sleep 5
        fi
    done
}

# Remove previous Log files
remove_log_files(){
    echo "Removing previous log files ..."
    for service in $SERVICES; do
        LOG_FILE="${service}_service.log"
        echo "Removing $LOG_FILE..."
        if [ -f $LOG_FILE ]; then
            rm -f $LOG_FILE
            echo "$LOG_FILE removed."
        else
            echo "$LOG_FILE does not exist."
        fi
    done
}

# Function to start services
start_services() {
    rm -f $PID_FILE

    for service in $SERVICES; do
        LOG_FILE="${service}_service.log"
        echo "Building and running $service..."
        cd ./$service || { echo "Directory $service not found. Exiting."; exit 1; }
        mvn clean install > ../$LOG_FILE 2>&1
        mvn spring-boot:run >> ../$LOG_FILE 2>&1 &
        SERVICE_PID=$!
        echo "$SERVICE_PID" >> ../$PID_FILE
        cd - || { echo "Failed to return to the root directory. Exiting."; exit 1; }
        echo "$service started with PID $SERVICE_PID. Logging to $LOG_FILE."
    done

    echo "All services have been started."
}

# Function to stop services
stop_services() {
    if [[ -f $PID_FILE ]]; then
        while IFS= read -r pid; do
            echo "Stopping service with PID $pid..."
            kill "$pid" || echo "Failed to stop service with PID $pid."
        done < "$PID_FILE"
        rm -f $PID_FILE
    fi
}

# Function to stop Docker Compose containers
stop_docker_containers() {
    echo "Stopping Docker containers..."
    docker-compose -f $DOCKER_COMPOSE_FILE down
}

# Trap SIGINT (Ctrl+C) to ensure services and containers are stopped when the script is interrupted
trap 'stop_services; stop_docker_containers; exit' INT TERM

# Start Docker containers
start_docker_containers

# Wait for Docker containers to be ready
wait_for_docker_containers

# Remove Log files
remove_log_files

# Start services
start_services

# Wait for all services to be stopped manually
while :; do
    sleep 1
done
