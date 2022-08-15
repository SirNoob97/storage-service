#!/bin/env bash

set -eu -o pipefail

output_dir=reduced-jre

[ -d $output_dir ] && rm -rf $output_dir

classpath=$(./gradlew -q showClassPath)

modules=$(jdeps -classpath $classpath \
  -recursive \
  -summary \
  --multi-release 17 \
  build/libs/storage_service.jar | \
  grep -Eo '\s\w{1,}\.\w{1,}$' | \
  sort | \
  uniq)

modules=$(echo $modules | tr ' ' ',')

jlink --add-modules "${modules},java.security.jgss,java.transaction.xa" \
  --strip-debug \
  --no-man-pages \
  --no-header-files \
  --compress 2 \
  --output $output_dir
