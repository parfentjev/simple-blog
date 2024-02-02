# Service

This API service is designed for managing blog content.

## Configuration

To configure the service, create a `.env` file containing the necessary key-value pairs. Refer to `.env.example` for the required format.

## Running Locally

To run the service locally, first load the environment variables:

```shell
set -a
source .env
set +a
```

This method ensures that all variables in `.env` are exported as environment variables. More details can be found [here](https://stackoverflow.com/a/43267603).

Next, initiate the service using:

```shell
make run
```

## Running in Docker

For Docker deployment, build and launch the service using the following command:

```
docker-compose up --build -d
```

This command builds the Docker image and starts the service in detached mode.

## Code Generation

The service relies on automatically generated server handlers and models, derived from the OpenAPI specification located in `spec/openapi.yaml`. SQL queries are also generated from `spec/query.sql`.

To set up the necessary tools for code generation, install them with:

```shell
go install github.com/deepmap/oapi-codegen/v2/cmd/oapi-codegen@latest
go install github.com/sqlc-dev/sqlc/cmd/sqlc@latest
```

If there are any modifications to the specifications, regenerate the code using:

```shell
make codegen
```

This ensures that the server code and SQL queries are up-to-date with the specifications.
