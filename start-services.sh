#!/bin/bash

# Configuration
SERVICES="eureka-server config-server api-gateway authentication-service user-service notification-service metamodel-service"
DB_COMPOSE_FILE="docker-compose-db.yml"
SERVERS_COMPOSE_FILE="docker-compose-servers.yml"
SERVICES_COMPOSE_FILE="docker-compose-services.yml"
DOCKER_COMPOSE_TIMEOUT=60  
DOCKER_SERVICE_NETWORK="healthcare-service-network"
log_dir="$(pwd)/logs"

# Function to start Docker Compose for a specific file
start_docker_compose() {
    local compose_file=$1
    echo "Starting Docker Compose for $compose_file..."
    docker-compose -f "$compose_file" up -d
    if [ $? -ne 0 ]; then
        echo "Failed to start Docker containers from $compose_file. Exiting."
        exit 1
    fi
}

# Function to wait for Docker containers to be ready
wait_for_docker_containers() {
    local compose_file=$1
    echo "Waiting for Docker containers from $compose_file to be ready..."
    sleep 10  

    local timeout=$DOCKER_COMPOSE_TIMEOUT
    while [[ $timeout -gt 0 ]]; do
        CONTAINER_STATUS=$(docker-compose -f "$compose_file" ps --filter "status=running" --quiet)
        if [[ -n $CONTAINER_STATUS ]]; then
            echo "All Docker containers from $compose_file are up and running."
            return 0
        else
            echo "Docker containers from $compose_file are not ready yet. Waiting..."
            sleep 5
            timeout=$((timeout - 5))
        fi
    done

    echo "Timeout waiting for Docker containers from $compose_file. Exiting."
    exit 1
}

# Remove previous log files
remove_log_files() {
    echo "Removing previous log files ..."
    if [ -d "$log_dir" ]; then
        rm -rf "$log_dir"
        echo "All log files in $log_dir removed."
    else
        echo "$log_dir directory does not exist."
    fi
}

# Function to start services (one by one with their own docker-compose file)
start_services() {
    local compose_file=$1
    echo "Starting services from $compose_file ..."
    mkdir -p "$log_dir"
    for service in $SERVICES; do
        LOG_FILE="$log_dir/${service}_service.log"
        echo "Starting $service with Docker Compose..."

        docker-compose -f "$compose_file" up -d "$service" > "$LOG_FILE" 2>&1

        local timeout=$DOCKER_COMPOSE_TIMEOUT
        while [[ $timeout -gt 0 ]]; do

            if docker-compose -f "$compose_file" ps | grep "$service" | grep "Up"; then
                echo "$service started successfully."
                break
            else
                echo "The docker containers of $service are not ready yet. Waiting..."
                sleep 5
                timeout=$((timeout - 5))
            fi

            if [[ $timeout -le 0 ]]; then
                echo "$service failed to start within the timeout period. Check the log: $LOG_FILE"
                return 1
            fi
        done
    done

    echo "All services have been started."
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
    docker-compose -f "$DB_COMPOSE_FILE" down
    docker-compose -f "$SERVERS_COMPOSE_FILE" down
    docker-compose -f "$SERVICES_COMPOSE_FILE" down
}

# Remove Network
remove_docker_network() {
    echo "Removing Docker network..."
    docker network remove "$DOCKER_SERVICE_NETWORK" 
}

# Trap SIGINT (Ctrl+C) to ensure services and containers are stopped when the script is interrupted
trap 'stop_docker_containers; remove_docker_network; echo "Application has been stopped! Good Bye!"; exit' INT TERM

# Check if the network exists
check_or_create_network "$DOCKER_SERVICE_NETWORK" 

# Start database containers
start_docker_compose "$DB_COMPOSE_FILE"
wait_for_docker_containers "$DB_COMPOSE_FILE"

# Start server containers
start_docker_compose "$SERVERS_COMPOSE_FILE"
wait_for_docker_containers "$SERVERS_COMPOSE_FILE"

# Remove Log files
remove_log_files

# Start service containers
start_services "$SERVICES_COMPOSE_FILE" 

# Wait for all services to be stopped manually
while :; do
    sleep 1
done
