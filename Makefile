SHELL := bash
.ONESHELL:
.SHELLFLAGS := -eu -o pipefail -c
.DELETE_ON_ERROR:

ifeq ($(origin .RECIPEPREFIX), undefined)
  $(error This Make does not support .RECIPEPREFIX. Please use GNU Make 4.0 or later)
endif
.RECIPEPREFIX = >

COMPOSE_CMD=docker compose
COMPOSE_OPTS=--env-file ./env/postgreSQL --env-file ./env/storage_service
GRADLE=./gradlew
EXCLUDE_TEST=--exclude-task test

all: deploy

deploy: build-container
> @$(COMPOSE_CMD) $(COMPOSE_OPTS) up -d

build-container:
> @$(COMPOSE_CMD) $(COMPOSE_OPTS) build

build-jar: clean compile-java
> @$(GRADLE) $(EXCLUDE_TEST) --info bootJar

test: clean compile-test
> @$(GRADLE) --info test

clean:
> @$(GRADLE) --quiet clean

compile-java:
> @$(GRADLE) --quiet compileJava

compile-test:
> @$(GRADLE) --quiet compileTest

.PHONY: deploy build-container build-jar test clean compile-java compile-test