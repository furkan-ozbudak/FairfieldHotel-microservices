#!/usr/bin/env bash
mvn clean
mvn install -Dmaven.test.skip=true
docker build -t eaprojectsx/orderdish .
docker push eaprojectsx/orderdish
