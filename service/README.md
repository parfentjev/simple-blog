# Service

This is a simple API service for my blog.

## Running locally

Before running the service, load env variables with:

```shell
export $(xargs <.env)
```

Source: [https://stackoverflow.com/revisions/50422066/1](https://stackoverflow.com/revisions/50422066/1)

Then start the service:

```shell
make run
```

## Running in Docker

Create an `.env` file with necessary parameters, then build and start the service with:

```
docker-compose up --build -d
```

## Code generation

Server handlers and models are generated automatically based on the OpenAPI specification defined in `spec/openapi.yaml`. Same goes for sql queries, see `spec/query.sql`.

Install the required tools:

```shell
go install github.com/deepmap/oapi-codegen/v2/cmd/oapi-codegen@latest
go install github.com/sqlc-dev/sqlc/cmd/sqlc@latest
```

Then regenerate the code if either spec has been modified:

```shell
make codegen
```
