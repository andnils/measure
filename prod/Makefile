DIST_DIR = ./dist
CLJ_SRC_DIR = ../src/main/clj/*
UBERJAR = ../target/uberjar/measure-0.1.0-SNAPSHOT-standalone.jar
COPY_UBERJAR = $(DIST_DIR)/measure.jar
STATIC_FILES = ../dist/*
JS_SRC_DIR = ../src/main/js/
COPY_STATIC_FILES = $(DIST_DIR)/static/
NODE_MODULES = ../node_modules/

prod : $(COPY_UBERJAR) $(COPY_STATIC_FILES)
	docker-compose build

uberjar : $(UBERJAR)

$(UBERJAR) : $(CLJ_SRC_DIR)
	lein uberjar

$(COPY_UBERJAR) : $(UBERJAR)
	mkdir -p $(DIST_DIR)
	cp $(UBERJAR) $(COPY_UBERJAR)

statics : $(COPY_STATIC_FILES)

$(NODE_MODULES) : ../package.json
	npm install

$(STATIC_FILES) : $(JS_SRC_DIR) $(NODE_MODULES)
	npm run prod


$(COPY_STATIC_FILES) : $(STATIC_FILES)
	mkdir -p $(DIST_DIR)/static
	cp -r $(STATIC_FILES) $(DIST_DIR)/static/

clean :
	rm -r $(DIST_DIR)
