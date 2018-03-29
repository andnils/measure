DIST_DIR = ./dist
JAR_SOURCE_FILE = ../target/uberjar/measure-0.1.0-SNAPSHOT-standalone.jar
JAR_TARGET_FILE = $(DIST_DIR)/measure.jar
JS_SOURCE_BUNDLE = ../dist/bundle.js
JS_TARGET_BUNDLE = $(DIST_DIR)/bundle.js
CLOJURE_SRC = ../src/main/clj/measure/*
JS_SRC = ../src/main/js

prod : $(JAR_TARGET_FILE) $(JS_TARGET_BUNDLE)
	docker-compose build

$(JAR_SOURCE_FILE) : $(CLOJURE_SRC)
	lein uberjar

$(JS_SOURCE_BUNDLE) : $(JS_SRC)
	npm run prod

$(JAR_TARGET_FILE) : $(JAR_SOURCE_FILE)
	mkdir -p $(DIST_DIR)
	cp $(JAR_SOURCE_FILE) $(JAR_TARGET_FILE)

$(JS_TARGET_BUNDLE) : $(JS_SOURCE_BUNDLE)
	mkdir -p $(DIST_DIR)
	cp $(JS_SOURCE_BUNDLE) $(JS_TARGET_BUNDLE)

clean :
	rm $(JAR_TARGET_FILE)
	rm $(JS_TARGET_BUNDLE)
