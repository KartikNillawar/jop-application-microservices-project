#!/bin/bash

# Retrieve environment variables from Docker Compose
DB_USERNAME="$POSTGRES_USER"
DB_PASSWORD="$POSTGRES_PASSWORD"
DB_NAME="$POSTGRES_DB"

# Create database using environment variables
psql -U "$DB_USERNAME" -c "CREATE DATABASE $POSTGRES_DB_COMPANY;"
psql -U "$DB_USERNAME" -c "CREATE DATABASE $POSTGRES_DB_REVIEW;"
psql -U "$DB_USERNAME" -c "CREATE DATABASE $POSTGRES_DB_JOB;"

echo "Database $DB_NAME created successfully."
