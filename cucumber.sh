#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

LIB=build/lib
CLASSES=build/classes
REPOSITORIO=https://repo1.maven.org/maven2

DEPENDENCIAS=(
  io.cucumber:cucumber-java:8.0.4
  io.cucumber:cucumber-picocontainer:8.0.4
  io.cucumber:cucumber-core:8.0.4
  io.cucumber:cucumber-gherkin:8.0.4
  io.cucumber:cucumber-gherkin-messages:8.0.4
  io.cucumber:cucumber-plugin:8.0.4
  io.cucumber:datatable:8.0.4
  io.cucumber:docstring:8.0.4
  io.cucumber:ci-environment:15.0.0
  io.cucumber:cucumber-expressions:20.1.0
  io.cucumber:cucumber-json-formatter:0.4.2
  io.cucumber:gherkin:42.0.1
  io.cucumber:html-formatter:24.2.0
  io.cucumber:junit-xml-formatter:0.15.0
  io.cucumber:messages:34.2.1
  io.cucumber:messages-ndjson:0.5.2
  io.cucumber:pretty-formatter:4.1.0
  io.cucumber:query:16.4.0
  io.cucumber:tag-expressions:11.0.1
  io.cucumber:teamcity-formatter:0.3.1
  io.cucumber:testng-xml-formatter:0.9.0
  io.cucumber:usage-formatter:0.2.1
  org.apiguardian:apiguardian-api:1.1.2
  org.jspecify:jspecify:1.0.1
  org.picocontainer:picocontainer:2.16
)

mkdir -p "$LIB"
for dependencia in "${DEPENDENCIAS[@]}"; do
  IFS=: read -r grupo artefato versao <<< "$dependencia"
  jar="$LIB/$artefato-$versao.jar"
  if [ ! -f "$jar" ]; then
    echo "baixando $artefato $versao"
    curl -fsSL -o "$jar.parcial" "$REPOSITORIO/${grupo//.//}/$artefato/$versao/$artefato-$versao.jar"
    mv "$jar.parcial" "$jar"
  fi
done

CAMINHOS=(features)
for argumento in "$@"; do
  case "$argumento" in
    features/*) CAMINHOS=() ;;
  esac
done

rm -rf "$CLASSES"
javac -encoding UTF-8 -cp "$LIB/*" -d "$CLASSES" steps/*.java
java -cp "$LIB/*:$CLASSES" io.cucumber.core.cli.Main --glue steps --plugin pretty "${CAMINHOS[@]}" "$@"
