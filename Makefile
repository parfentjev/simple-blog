.PHONY: dev versions

dev:
	export $(cat .env | xargs)
	mvn spring-boot:run

versions:
	mvn versions:use-next-releases
