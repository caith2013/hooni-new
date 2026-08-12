#!/bin/bash

# --- CONFIGURATION SETTINGS ---
REGISTRY_IP="192.168.2.2:32000"
IMAGE_NAME="hooni-app"
CHART_PATH="./hooni-chart"
NAMESPACE="hooni"
RELEASE_NAME="hooni"

# Exit immediately if a command exits with a non-zero status
set -e

echo "==================================================="
echo " Starting Build & Deploy Pipeline"
echo "==================================================="

# --- STEP 1: READ VERSION FROM POM.XML ---
echo "[1/4] Extracting version from pom.xml..."
if [ ! -f "pom.xml" ]; then
    echo "ERROR: pom.xml not found in the current directory."
    exit 1
fi

# Extract version using grep and sed safely without external XML parsers
VERSION=$(grep -m 1 "<version>" pom.xml | sed -E 's/.*<version>([^<]+)<\/version>.*/\1/')

if [ -z "$VERSION" ]; then
    echo "ERROR: Could not find version block in pom.xml"
    exit 1
fi
echo "Found App Version: $VERSION"

# --- STEP 2: BUILD SPRING BOOT JAR ---
echo "[2/4] Packaging Spring Boot Application with Maven..."
mvn clean package -DskipTests

# --- STEP 3: DOCKER BUILD AND TAG ---
echo "[3/4] Building and Tagging Docker Image..."
FULL_IMAGE_TAG="${REGISTRY_IP}/${IMAGE_NAME}:${VERSION}"

docker build -t "${IMAGE_NAME}:${VERSION}" .
docker tag "${IMAGE_NAME}:${VERSION}" "${FULL_IMAGE_TAG}"

# --- STEP 4: PUSH TO MICROK8S REGISTRY ---
echo "[4/4] Pushing image to MicroK8s Registry at ${REGISTRY_IP}..."
docker push "${FULL_IMAGE_TAG}"

echo "==================================================="
echo " SUCCESS: Image ${FULL_IMAGE_TAG} pushed!"
echo "==================================================="
echo "Run this command on your MicroK8s master node to deploy:"
echo "microk8s helm3 upgrade ${RELEASE_NAME} ${CHART_PATH} -n ${NAMESPACE} --set hooniApp.image=${FULL_IMAGE_TAG}"
echo "==================================================="
