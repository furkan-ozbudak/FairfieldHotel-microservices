#!/usr/bin/env bash
kubectl delete -n default deployment ea-gateway-deployment
kubectl delete -n default service ea-gateway-service
kubectl apply -f deploy.yml