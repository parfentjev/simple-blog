.PHONY: codegen
codegen:
	rm -rf ./codegen/src/main/java/ee/fakeplastictrees/blog/codegen/*
	docker run --rm \
			-v ./spec:/workdir/spec:ro \
			-v ./codegen/src/main/java/ee/fakeplastictrees/blog/codegen/api:/workdir/out/src/main/java/ee/fakeplastictrees/blog/codegen/api \
			-v ./codegen/src/main/java/ee/fakeplastictrees/blog/codegen/model:/workdir/out/src/main/java/ee/fakeplastictrees/blog/codegen/model \
			openapitools/openapi-generator-cli generate \
			-c /workdir/spec/swagger-config.yaml \
			-i /workdir/spec/swagger.yaml \
			-g spring \
			-o /workdir/out

.PHONY: build
build:
	docker build -t simple-blog-backend:latest .
	docker save simple-blog-backend:latest > simple-blog-backend.tar

.PHONY: push
push:
	scp simple-blog-backend.tar $(FPT_BACKEND_PWD)
