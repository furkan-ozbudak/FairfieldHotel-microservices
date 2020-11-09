#!/usr/bin/env bash
kubectl delete -n default deployment ea-chat-deployment
kubectl delete -n default service ea-chat-service
kubectl apply -f deploy.yml