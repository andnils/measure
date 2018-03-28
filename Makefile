JAR_FILE = target/uberjar/measure-0.1.0-SNAPSHOT-standalone.jar
JS_BUNDLE = dist/bundle.js
CLOJURE_SRC = src/main/clj/measure/*
JS_SRC = src/main/js

prod : $(JAR_FILE) $(JS_BUNDLE)
	docker-compose build

$(JAR_FILE) : $(CLOJURE_SRC)
	lein uberjar

$(JS_BUNDLE) : $(JS_SRC)
	npm run prod
