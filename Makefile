.PHONY:minify
.PHONY:deploy

minify:
	cd data/static/ && \
	minify index.css > index.min.css && \
	minify index.js > index.min.js

deploy:
	bash deploy.sh