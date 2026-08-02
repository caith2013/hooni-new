#!/bin/bash
set -e

NAMESPACE="${1:-hooni}"

echo "Using namespace: ${NAMESPACE}"

echo "Applying PVC..."
microk8s kubectl apply -n "${NAMESPACE}" -f mysql-backup-pvc.yaml

echo "Creating loader pod..."
microk8s kubectl apply -n "${NAMESPACE}" -f mysql-backup-loader.yaml

echo "Waiting for loader pod..."
microk8s kubectl wait -n "${NAMESPACE}" --for=condition=Ready pod/backup-loader --timeout=60s

echo "Copying backup.sql into PVC..."
microk8s kubectl cp -n "${NAMESPACE}" mydb-2026.sql backup-loader:/backup/backup.sql

echo "Verifying file..."
microk8s kubectl exec -n "${NAMESPACE}" backup-loader -- sh -c "ls -l /backup"

echo "Cleaning up loader pod..."
microk8s kubectl delete pod -n "${NAMESPACE}" backup-loader

echo "Done."
