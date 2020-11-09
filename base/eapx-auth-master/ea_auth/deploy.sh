#!/usr/bin/env bash
kubectl delete -n default deployment ea-auth-deployment
kubectl delete -n default service ea-auth-service
kubectl apply -f deploy.yml