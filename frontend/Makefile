.PHONY: run
run:
	BROWSER=none npm run dev

.PHONY: codegen
codegen:
	rm -rf ./codegen/src/main/java/ee/fakeplastictrees/blog/codegen/*
	docker run --rm \
		-v ./../backend/spec:/workdir/spec:ro \
		-v ./src/api/codegen:/workdir/out \
		openapitools/openapi-generator-cli generate \
		-i /workdir/spec/swagger.yaml \
		-g typescript-fetch \
		-o /workdir/out \
		--additional-properties hooks=true

.PHONY: build
build:
	docker buildx use container-builder
	docker buildx build --platform linux/arm64 -t simple-blog-frontend:latest --output type=docker,dest=image.tar \
		--build-arg VITE_SERVICE_URL="https://service.fakeplastictrees.ee" \
		--build-arg VITE_MEDIA_URL="https://www.fakeplastictrees.ee/media/" \
		.

.PHONY: push
push:
	rsync image.tar $(FPT_FRONTEND_PWD)
