#!/bin/sh

set -eu -o pipefail

output_dir=reduced-jre

[ -d $output_dir ] && rm -rf $output_dir

classpath=$(./gradlew -q showClassPath)

modules=$(jdeps -classpath $classpath \
  --multi-release 17 \
  --print-module-deps \
  --ignore-missing-deps \
  -recursive \
  build/libs/storage_service.jar)

jlink --add-modules $modules \
  --strip-debug \
  --no-man-pages \
  --no-header-files \
  --compress 2 \
  --output $output_dir
