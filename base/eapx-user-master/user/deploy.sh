#!/usr/bin/env bash
kubectl delete -n default deployment ea-user-deployment
kubectl delete -n default service ea-user-service
kubectl apply -f deploy.yml