@echo off
SETLOCAL EnableDelayedExpansion

:: --- CONFIGURATION SETTINGS ---
SET REGISTRY_IP=192.168.2.2:32000
SET IMAGE_NAME=hooni-app
SET CHART_PATH=./hooni-chart
SET NAMESPACE=hooni
SET RELEASE_NAME=hooni

:: --- STEP 1: READ VERSION FROM POM.XML ---
echo [1/4] Extracting version from pom.xml...
for /f "tokens=2 delims=><" %%a in ('findstr /R "<version>[0-9]" pom.xml') do (
    set VERSION=%%a
    goto :found_version
)
:found_version
if "%VERSION%"=="" (
    echo ERROR: Could not find version in pom.xml
    exit /b 1
)
echo Found App Version: %VERSION%

:: --- STEP 2: BUILD SPRING BOOT JAR ---
echo [2/4] Packaging Spring Boot Application with Maven...
call mvn clean package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven build failed.
    exit /b 1
)

:: --- STEP 3: DOCKER BUILD AND TAG ---
echo [3/4] Building and Tagging Docker Image...
set FULL_IMAGE_TAG=%REGISTRY_IP%/%IMAGE_NAME%:%VERSION%

docker build -t %IMAGE_NAME%:%VERSION% .
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Docker build failed.
    exit /b 1
)

docker tag %IMAGE_NAME%:%VERSION% %FULL_IMAGE_TAG%

:: --- STEP 4: PUSH TO MICROK8S REGISTRY ---
echo [4/4] Pushing image to MicroK8s Registry at %REGISTRY_IP%...
docker push %FULL_IMAGE_TAG%
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Docker push failed. Ensure Insecure Registries are configured in Docker Desktop.
    exit /b 1
)

echo ===================================================
echo SUCCESS: Image %FULL_IMAGE_TAG% pushed successfully!
echo ===================================================
echo Run this command on your MicroK8s master node to deploy:
echo microk8s helm3 upgrade %RELEASE_NAME% %CHART_PATH% -n %NAMESPACE% --set hooniApp.image=%FULL_IMAGE_TAG%
echo ===================================================

pause
ENDLOCAL
