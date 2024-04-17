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
