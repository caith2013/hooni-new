#!/bin/bash
set -e

echo "Applying PVC..."
kubectl apply -f mysql-backup-pvc.yaml

echo "Creating loader pod..."
kubectl apply -f mysql-backup-loader.yaml

echo "Waiting for loader pod..."
kubectl wait --for=condition=Ready pod/backup-loader --timeout=60s

echo "Copying backup.sql into PVC..."
kubectl cp mydb-2026.sql backup-loader:/backup/backup.sql

echo "Verifying file..."
kubectl exec backup-loader -- sh -c "ls -l /backup"

echo "Cleaning up loader pod..."
kubectl delete pod backup-loader

echo "Done."
