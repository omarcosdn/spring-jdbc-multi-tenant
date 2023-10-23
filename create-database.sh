#!/bin/bash

set -e
set -u

function create_user_and_database() {
    local database=$1
    echo "  Creating user and database '$database'"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
        CREATE USER $database;
        CREATE DATABASE $database;
        GRANT ALL PRIVILEGES ON DATABASE $database TO $database;
EOSQL
}

function create_company_datasource_table() {
    local database=$1
    echo "  Creating table company_datasource in '$database'"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$database" <<-EOSQL
        CREATE TABLE company_datasource (
          company_id uuid NOT NULL,
          datasource_name varchar NOT NULL,
          CONSTRAINT company_datasource_pk PRIMARY KEY (company_id)
        );
EOSQL
}

if [ -n "$POSTGRES_TENANT_CONFIG_DATABASE" ]; then
    echo "Tenant config database creation requested: $POSTGRES_TENANT_CONFIG_DATABASE"
    create_user_and_database "$POSTGRES_TENANT_CONFIG_DATABASE"
    create_company_datasource_table "$POSTGRES_TENANT_CONFIG_DATABASE"
    echo "Tenant config database created"
fi

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
    echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
    for db in $(echo "$POSTGRES_MULTIPLE_DATABASES" | tr ',' ' '); do
        create_user_and_database "$db"
    done
    echo "Multiple databases created"
fi