#!/usr/bin/env bash
mvn clean
mvn install -Dmaven.test.skip=true
docker build -t eaprojectsx/foodordering .
docker push eaprojectsx/foodordering
