#!/bin/bash

# Configuration
SERVICES="eureka-server config-server api-gateway authentication-serviceuser-service metamodel-service medicalrecord-service notification-service"

DOCKER_COMPOSE_FILE_db_CONTAINERS="docker-compose-db.yml"
DOCKER_COMPOSE_FILE_kafka_CONTAINERS="docker-compose-servers.yml"
DOCKER_SERVICE_NETWORK="healthcare-service-network"
log_dir="$(pwd)/logs"
PID_FILE="$log_dir/pids.txt"

# Function to start Docker containers
start_docker_containers() {
    local docker_compose_file="$1"  
    echo "Starting Docker containers using $docker_compose_file..."
    docker-compose -f "$docker_compose_file" up -d
    if [ $? -ne 0 ]; then
        echo "Failed to start Docker containers. Exiting."
        exit 1
    fi
}

# Function to check if Docker containers are ready
wait_for_docker_containers() {
    echo "Waiting for Docker containers to be ready..."
    sleep 10  

    while true; do
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

# Remove previous log files
remove_log_files() {
    echo "Removing previous log files ..."
    if [ -d "$log_dir" ]; then
        rm -rf "$log_dir"/*
        echo "All log files in $log_dir removed."
    else
        echo "$log_dir directory does not exist."
    fi
}

# Function to start services
start_services() {
    
    if mkdir -p "$log_dir"; then
        echo "Log directory created: $log_dir"
    else
        echo "Failed to create log directory: $log_dir"
        exit 1
    fi

    for service in $SERVICES; do
        LOG_FILE="$log_dir/${service}_service.log"
        echo "Building and running $service..."
        cd ./$service || { echo "Directory $service not found. Exiting."; exit 1; }
        mvn clean install -DskipTests -Pdev > $LOG_FILE 2>&1
        mvn spring-boot:run -Pdev >> $LOG_FILE 2>&1 &
        SERVICE_PID=$!
        echo "$SERVICE_PID" >> $PID_FILE
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

# Function to check if a Docker network exists or create it if it doesn't
check_or_create_network() {
    local network_name=$1

    if ! docker network inspect "$network_name" >/dev/null 2>&1; then
        echo "Network $network_name does not exist. Creating..."
        
        docker network create "$network_name" 2>&1
        if [ $? -ne 0 ]; then
            echo "Failed to create network $network_name. Exiting."
            echo "Error creating network: $(docker network create "$network_name" 2>&1)"
            exit 1
        else
            echo "Network $network_name created successfully."
        fi
    else
        echo "Network $network_name already exists."
    fi
}

# Function to stop Docker Compose containers
stop_docker_containers() {
    echo "Stopping Docker containers..."
    docker-compose -f $DOCKER_COMPOSE_FILE down
}

# Remove Network
remove_docker_network() {
    echo "Removing Docker network..."
    docker network remove "$DOCKER_SERVICE_NETWORK" 
}

# Trap SIGINT (Ctrl+C) to ensure services and containers are stopped when the script is interrupted
trap 'stop_services; stop_docker_containers; remove_docker_network; echo "Application has been stopped! Good Bye!"; exit' INT TERM

# Check if the network exists
check_or_create_network "$DOCKER_SERVICE_NETWORK" 

# Start Docker containers
start_docker_containers "$DOCKER_COMPOSE_FILE_db_CONTAINERS"
start_docker_containers "$DOCKER_COMPOSE_FILE_kafka_CONTAINERS"

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