.PHONY: run
run:
	BROWSER=none npm start

.PHONY: codegen
codegen:
	rm -rf src/api/codegen
	npx openapi-generator-cli generate -i ../service/spec/openapi.yaml \
		-g typescript-fetch \
		-o src/api/codegen \
		--additional-properties hooks=true

.PHONY: build
build:
	npm run build

.PHONY: deploy
deploy:
	docker build -t simple-blog-frontend:latest --build-arg REACT_APP_SERVICE_URL="http://service.fakeplastictrees.ee" .
	docker save simple-blog-frontend:latest > simple-blog-frontend.tar
	gzip -f simple-blog-frontend.tar
	scp simple-blog-frontend.tar.gz $(FPT_FRONTEND_PWD)
