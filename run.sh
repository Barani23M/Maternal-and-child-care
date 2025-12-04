#!/bin/bash
# Build and Run Script for Maternal Care Backend (Linux/Mac)

echo "============================================================"
echo "  PREGNANCY BABY CARE SYSTEM - Spring Boot Backend"
echo "============================================================"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "[ERROR] Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

echo "[INFO] Java found:"
java -version
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "[ERROR] Maven is not installed or not in PATH"
    echo "Please install Maven 3.8 or higher"
    exit 1
fi

echo "[INFO] Maven found:"
mvn -version
echo ""

echo "============================================================"
echo "  Building the application..."
echo "============================================================"
mvn clean install -DskipTests
if [ $? -ne 0 ]; then
    echo "[ERROR] Build failed!"
    exit 1
fi

echo ""
echo "============================================================"
echo "  Starting the application..."
echo "============================================================"
echo ""
echo "Application will be available at:"
echo "  - API: http://localhost:8080/api"
echo "  - Swagger UI: http://localhost:8080/swagger-ui.html"
echo "  - H2 Console: http://localhost:8080/h2-console"
echo ""
echo "Test accounts:"
echo "  - Admin:  admin@maternal.com  / admin123"
echo "  - Doctor: doctor@maternal.com / doctor123"
echo "  - User:   user@maternal.com   / user123"
echo ""
echo "============================================================"

mvn spring-boot:run
