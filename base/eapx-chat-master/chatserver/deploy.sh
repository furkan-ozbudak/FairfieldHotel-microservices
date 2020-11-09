#!/usr/bin/env bash
kubectl delete -n default deployment ea-chatserver-deployment
kubectl delete -n default service ea-chatserver-service
kubectl apply -f deploy.yml