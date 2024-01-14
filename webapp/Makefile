.PHONY: release
.PHONY: deploy

release:
	npm run build

deploy:
	ssh ${SIMPLE_BLOG_REMOTE_HOST} "rm -rf ${SIMPLE_BLOG_REMOTE_PATH}webapp/*"
	scp -r build/* ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PATH}webapp/
