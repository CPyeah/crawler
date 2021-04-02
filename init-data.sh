#!/bin/bash

echo "init h2 database!"
rm -rf ./src/main/resources/news.mv.db
rm -rf ./src/main/resources/news.trace.db
echo "remove old database ok!"
mvn flyway:migrate
echo "init database ok!"
