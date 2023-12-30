.PHONY:minify
.PHONY:deploy

minify:
	cd data/static/ && \
	minify index.css > index.min.css && \
	minify index.js > index.min.js

deploy:
	docker buildx build -t simple-blog . && \
	docker save simple-blog:latest > image.tar && \
	scp image.tar ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PWD}image.tar && \
	ssh ${SIMPLE_BLOG_REMOTE_HOST} "cd ${SIMPLE_BLOG_REMOTE_PWD} && docker load < image.tar && docker-compose up -d" && \
	scp ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PWD}service.db service.prod.db && \
	diesel --database-url service.prod.db migration run && \
	scp service.prod.db ${SIMPLE_BLOG_REMOTE_HOST}:${SIMPLE_BLOG_REMOTE_PWD}service.db && \
	rm service.prod.db && \
	rm image.tar