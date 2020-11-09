#!/usr/bin/env bash
mvn clean
mvn install -Dmaven.test.skip=true
docker build -t eaprojectsx/eapx-chatserver .
docker push eaprojectsx/eapx-chatserver