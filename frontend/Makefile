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
	docker build -t simple-blog-frontend:latest \
		--build-arg VITE_SERVICE_URL="https://service.fakeplastictrees.ee" \
		--build-arg VITE_MEDIA_URL="https://www.fakeplastictrees.ee/media/" \
		.
	docker save simple-blog-frontend:latest > simple-blog-frontend.tar

.PHONY: push
push:
	scp simple-blog-frontend.tar $(FPT_FRONTEND_PWD)
