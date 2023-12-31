.PHONY:minify
.PHONY:deploy

minify:
	cd data/static/ && \
	minify index.css > index.min.css && \
	minify index.js > index.min.js

deploy:
	docker build -t simple-blog . && \
	docker save simple-blog:latest > image.tar && \
	scp image.tar ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PATH}image.tar && \
	ssh ${SIMPLE_BLOG_REMOTE_HOST} "cd ${SIMPLE_BLOG_REMOTE_PATH} && docker load < image.tar && docker-compose up -d" && \
	scp ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PATH}service.db service.prod.db && \
	diesel --database-url service.prod.db migration run && \
	scp service.prod.db ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PATH}service.db && \
	rm service.prod.db && \
	rm image.tar