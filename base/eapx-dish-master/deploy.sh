#!/usr/bin/env bash
kubectl delete -n default deployment foodordering
kubectl delete -n default service foodordering
kubectl apply -f deploy.yml
