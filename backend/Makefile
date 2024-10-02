.PHONY: deploy
deploy:
	docker build -t simple-blog-backend:latest .
	docker save simple-blog-backend:latest > simple-blog-backend.tar
	gzip -f simple-blog-backend.tar
	scp simple-blog-backend.tar.gz $(FPT_BACKEND_PWD)
