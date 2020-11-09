# #!/usr/bin/env bash
# helm repo add stable https://kubernetes-charts-incubator.storage.googleapis.com/
# helm repo add incubator https://kubernetes-charts-incubator.storage.googleapis.com/
# kubectl delete -n default configmap ea-configs
# kubectl delete -n default secret ea-secrets
# kubectl apply -f ea-secrets.yml
# kubectl apply -f ea-configs.yml
# helm delete --purge mongodb-service
# helm install --name mongodb-service stable/mongodb-replicaset
# helm delete --purge redis-service
# helm install --name redis-service stable/redis-ha
# helm delete --purge rabbitmq-service
# helm install --name rabbitmq-service stable/rabbitmq-ha
