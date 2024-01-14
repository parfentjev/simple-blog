.PHONY: run
.PHONY: codegen
.PHONY: deploy

run:
	go run .

codegen:
	sqlc generate

release:
	docker build -t simple-blog-service .
	docker save simple-blog-service:latest > simple-blog-service.tar

deploy:
	scp simple-blog-service.tar ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PATH}simple-blog-service.tar
	ssh ${SIMPLE_BLOG_REMOTE_HOST} "cd ${SIMPLE_BLOG_REMOTE_PATH} && \
		docker load < simple-blog-service.tar && \
		docker-compose up -d && \
		rm simple-blog-service.tar"
