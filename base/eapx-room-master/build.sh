#!/usr/bin/env bash
mvn clean
mvn install -Dmaven.test.skip=true
docker build -t eaprojectsx/eapx-room .
docker push eaprojectsx/eapx-room