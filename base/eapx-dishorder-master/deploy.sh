#!/usr/bin/env bash 
kubectl delete -n default deployment orderdish
kubectl delete -n default service orderdish
kubectl apply -f deploy.yml
