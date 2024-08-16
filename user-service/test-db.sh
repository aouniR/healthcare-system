#!/bin/sh
PGPASSWORD="E8C7D%j9@#G5Z!2u8q&1E8kLx9m^Wb7S"

# Ensure the environment variable is set
if [ -z "$PGPASSWORD" ]; then
  echo "Environment variable PGPASSWORD is not set. Exiting."
  exit 1
fi

echo "Testing database connection..."

# Retry connection until successful or timeout
timeout=300  # Timeout after 5 minutes
start_time=$(date +%s)

until psql -h localhost -p 5432 -U user_service -d userdb  '\q'; do
  current_time=$(date +%s)
  elapsed_time=$((current_time - start_time))

  if [ $elapsed_time -ge $timeout ]; then
    echo "Timeout reached. Database connection test failed."
    exit 1
  fi

  echo "Waiting for database... ($(date))"
  sleep 5
done

echo "Database connection successful."
