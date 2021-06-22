# Docker containers for Databricks Simba ODBC/JDBC drivers

This repo provides generic Docker containers for Databricks Simba ODBC/JDBC drivers.

# Instructions

## .env file setup

Please setup properties in the `.env` file accordingly to `.env.sample`
## ODBC

- build example via `make build-odbc`
- run example via `make run-odbc`
- example app and Dockerfile is located in `/odbc` directory
## JDBC

- build example via `make build-jdbc`
- run example via `run-jdbc`
- example app and Dockerfile is located in `/jdbc` directory



# Legal information

Please note that by downloading the driver during Docker build you agree with Terms and Conditions provided on the [official download page](https://databricks.com/spark/jdbc-drivers-download).