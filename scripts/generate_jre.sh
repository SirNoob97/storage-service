#!/bin/sh

set -eu -o pipefail

[ ! -f ./modules ] && echo "Modules file not found!!!" && exit 1

modules=$(cat ./modules)
output_dir=reduced-jre

[ -d $output_dir ] && rm -rf $output_dir

jlink --add-modules $modules \
  --strip-debug \
  --no-man-pages \
  --no-header-files \
  --compress 2 \
  --output $output_dir
