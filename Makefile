.PHONY: dev versions

dev:
	export $(cat .env | xargs) > /dev/null
	mvn spring-boot:run

versions:
	mvn versions:use-next-releases
