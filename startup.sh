#!/bin/bash
set -e

echo "Clearing out old builds and targets..."
#rm -rf backend/target backend/src/main/resources/static frontend/dist frontend/node_modules

echo "Extracting and installing Frontend Packages..."
cd frontend
#npm install --no-audit --no-fund

echo "Compiling Angular 18 Single Page Application..."
# Fix: Build locally to prevent relative-path compilation bugs
npx ng build --configuration=production

echo "Deploying compiled frontend assets to backend..."
mkdir -p ../backend/src/main/resources/static

# Safely copies built files regardless of your Angular workspace project name setup
if [ -d "dist/carsimulation/browser" ]; then
    cp -r dist/carsimulation/browser/* ../backend/src/main/resources/static/
elif [ -d "dist/browser" ]; then
    cp -r dist/browser/* ../backend/src/main/resources/static/
else
    cp -r dist/*/* ../backend/src/main/resources/static/ 2>/dev/null || cp -r dist/* ../backend/src/main/resources/static/
fi

cd ..

echo "Checking Spring Boot Engine Environment..."
cd backend

# Generate a wrapper if not present, otherwise run package
if [ ! -f "./mvnw" ]; then
    echo "⚠️ Maven wrapper missing, running fallback compile..."
    /C/Program\ Files/apache-maven-3.9.16/bin/mvn clean package -DskipTests
else
    chmod +x mvnw
    ./mvnw clean package -DskipTests
fi

echo "✅ Successfully Booted! Open http://localhost:8080 in your browser."
java -jar target/carsimulation-0.0.1-SNAPSHOT.jar