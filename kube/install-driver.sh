#!/bin/bash
set -euo pipefail

# This script sets up the standard SMB CSI driver in your cluster
REPO_URL="https://githubusercontent.com"

echo "Deploying SMB CSI Driver components..."
kubectl apply -f "${REPO_URL}/rbac-csi-smb-controller.yaml"
kubectl apply -f "${REPO_URL}/csi-smb-driver.yaml"
kubectl apply -f "${REPO_URL}/csi-smb-controller.yaml"
kubectl apply -f "${REPO_URL}/csi-smb-node.yaml"

echo "SMB CSI Driver installed successfully!"
