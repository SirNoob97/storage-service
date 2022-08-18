#!/bin/sh

set -eu -o pipefail

reduced_jre_file=./modules
classpath=$(./gradlew -q showClassPath)
jar=$(find . -type f -path */storage_service.jar)

[ -f $reduced_jre_file ] && [ ! -w $reduced_jre_file ] && rm -f $reduced_jre_file

jdeps -classpath $classpath \
  --multi-release 17 \
  --print-module-deps \
  --ignore-missing-deps \
  -recursive \
  $jar > $reduced_jre_file