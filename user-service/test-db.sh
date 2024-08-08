#!/bin/sh

# Ensure the environment variable is set
if [ -z "$PGPASSWORD" ]; then
  echo "Environment variable PGPASSWORD is not set. Exiting."
  exit 1
fi

echo "Testing database connection..."

# Retry connection until successful or timeout
timeout=300  # Timeout after 5 minutes
start_time=$(date +%s)

until psql -h user-service-db -U user_service -d userdb -c '\q'; do
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
