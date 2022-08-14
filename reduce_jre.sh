#!/bin/env bash

set -eu -o pipefail

classpath=$(./gradlew -q showClassPath)

# TODO: get java modules from this
jdeps --multi-release 17 -cp $classpath ./build/libs/storage_service.jar

# TODO: replace the modules wiht the output from jdeps
jlink --add-modules 'java.base,java.logging,java.desktop,java.naming,java.instrument,java.management,java.xml,java.security.jgss,java.sql,java.transaction.xa,jdk.unsupported' --strip-debug --no-man-pages --no-header-files --compress 2 --output reduced-jre
