# storage-service

File storage backend application.

This service stores files in a PostgreSQL database as BLOB(oid).

## Requirements

* [Java 17+](https://projects.eclipse.org/projects/adoptium.temurin)
* [Docker](https://www.docker.com/)

## SQL Diagram

![db-diagram](postgreSQL/db_diagram.png)

The `file_size` column store the size of the file as **bytes**.
