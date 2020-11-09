#!/usr/bin/env bash
mvn clean
mvn package -Dmaven.test.skip=true
docker build -t eaprojectsx/eapx-payment .
docker push eaprojectsx/eapx-payment
