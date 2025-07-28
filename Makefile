.PHONY: setup_docker
setup_docker:
	docker buildx create --use --name container-builder

.PHONY: build
build:
	docker buildx use container-builder
	docker buildx build --platform linux/arm64 -t simple-blog:latest --output type=docker,dest=image.tar .

.PHONY: push
push:
	rsync image.tar $(TARGET)
