#!/usr/bin/env bash
kubectl delete -n default deployment eapx-mail-deployment
kubectl delete -n default service eapx-mail-service
kubectl apply -f deploy.yml