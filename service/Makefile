.PHONY: run
.PHONY: migrate
.PHONY: codegen
.PHONY: deploy

run:
	go run .

migrate:
	sqlite3 data/server.db < spec/schema.sql

codegen:
	sqlc -f spec/sqlite.yaml generate
	oapi-codegen -package api -generate spec,types,gin -o internal/api/server.gen.go spec/openapi.yaml

release:
	docker build -t simple-blog-service .
	docker save simple-blog-service:latest > simple-blog-service.tar

deploy:
	scp simple-blog-service.tar ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PATH}simple-blog-service.tar
	ssh ${SIMPLE_BLOG_REMOTE_HOST} "cd ${SIMPLE_BLOG_REMOTE_PATH} && \
		docker load < simple-blog-service.tar && \
		docker-compose up -d && \
		rm simple-blog-service.tar"
	scp ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PATH}data/server.db data/server.prod.db
	sqlite3 data/server.prod.db < spec/schema.sql
	scp data/server.prod.db ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PATH}data/server.db
	rm data/server.prod.db
