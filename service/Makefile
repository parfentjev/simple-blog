.PHONY: run
run:
	go run .

.PHONY: migrate
migrate:
	sqlite3 data/server.db < spec/schema.sql

.PHONY: codegen
codegen:
	sqlc -f spec/sqlite.yaml generate
	oapi-codegen -package server -generate spec,types,gin -o internal/server/server.gen.go spec/openapi.yaml

.PHONY: generate_env_example
generate_env_example:
	@echo "# All key-value pairs are mandatory" > .env.example
	@echo "# Values with spaces should be wrapped in 'single quotes'" >> .env.example
	@echo "" >> .env.example
	@while IFS= read -r line || [[ -n "$$line" ]]; do \
		if [[ -n "$$line" && ! "$$line" =~ ^# ]]; then \
			key=$$(echo "$$line" | cut -d '=' -f 1); \
			echo "$$key=" >> .env.example; \
		fi \
	done < .env

.PHONY: release
release:
	$(MAKE) generate_env_example
	docker build -t simple-blog-service .
	docker save simple-blog-service:latest > simple-blog-service.tar

.PHONY: deploy
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
