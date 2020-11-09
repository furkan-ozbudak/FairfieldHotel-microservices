#!/usr/bin/env bash
kubectl delete -n default deployment ea-client-deployment
kubectl delete -n default service ea-client-service
kubectl apply -f ea_client_service.yml