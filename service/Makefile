.PHONY: run
run:
	go run .

.PHONY: migrate
migrate:
	sqlite3 data/server.db < spec/schema.sql

.PHONY: codegen
codegen:
	sqlc -f spec/sqlc.yaml generate
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
