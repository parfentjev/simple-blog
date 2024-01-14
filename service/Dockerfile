FROM golang:latest as builder
WORKDIR /service
COPY go.mod go.sum ./
RUN go mod download
COPY ./internal ./internal
COPY ./main.go ./main.go
RUN GOOS=linux go build -a -installsuffix cgo -o service .

FROM debian:bookworm-slim
RUN apt-get update
RUN apt-get install -y libsqlite3-0
RUN rm -rf /var/lib/apt/lists/*
RUN mkdir /data
COPY --from=builder /service/service /usr/local/bin/service

CMD ["service"]
