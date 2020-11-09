#!/usr/bin/env bash
mvn clean
mvn install -Dmaven.test.skip=true
docker build -t eaprojectsx/ea_gateway .
docker push eaprojectsx/ea_gateway