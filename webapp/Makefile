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

.PHONY: release
release:
	npm run build

.PHONY: deploy
deploy:
	ssh ${SIMPLE_BLOG_REMOTE_HOST} "rm -rf ${SIMPLE_BLOG_REMOTE_PATH}webapp/*"
	scp -r build/* ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PATH}webapp/
