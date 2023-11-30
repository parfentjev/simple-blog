FROM rust:1.71 as builder
WORKDIR /usr/src/simple_blog
COPY . .
RUN cargo install --path .

FROM debian:bookworm-slim
RUN apt-get update && apt-get install -y libsqlite3-0 && rm -rf /var/lib/apt/lists/*
COPY --from=builder /usr/local/cargo/bin/simple_blog /usr/local/bin/simple_blog
COPY --from=builder /usr/src/simple_blog/data/static/ /data/static/
COPY --from=builder /usr/src/simple_blog/data/templates/ /data/templates/
COPY --from=builder /usr/src/simple_blog/.env /.env
CMD ["simple_blog"]