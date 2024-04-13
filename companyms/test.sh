#!/bin/bash

# Set connection parameters
HOST="localhost"
USERNAME="admin"
PASSWORD="12345"

# Create test server
echo "Creating test server..."
pgadmin4 create_server \
--name="TestServer" \
--host="$HOST" \
--port="5432" \
--ssl="prefer" \
--maintenance_db="postgres" \
--username="admin" \
--password="12345" \
--role="Read Only" \
--comment="" \
--db_restrictions="" \
--roles="admin" \
--colour="#d0e0ff" \
--icon="ico-server" \
--connect_now="false" \
--password_required="true"

# Create job database
echo "Creating job database..."
pgadmin4 create_database \
--name="JobDatabase" \
--comment="Database for job management" \
--server="TestServer" \
--owner="admin" \
--template="template0" \
--encoding="UTF8" \
--collation="English_United States.1252" \
--ctype="English_United States.1252" \
--tablespace="" \
--connection_limit="-1" \
--allow_connections="true" \
--is_template="false" \
--tables_space="" \
--authid="admin" \
--comment_admin="" \
--comment_security=""
