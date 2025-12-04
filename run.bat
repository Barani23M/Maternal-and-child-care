@echo off
REM Build and Run Script for Maternal Care Backend (Windows)

echo ============================================================
echo   PREGNANCY BABY CARE SYSTEM - Spring Boot Backend
echo ============================================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

echo [INFO] Java found:
java -version
echo.

REM Set Maven path
set "MAVEN_HOME=%USERPROFILE%\maven"
set "PATH=%MAVEN_HOME%\bin;%PATH%"

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Maven is not installed or not in PATH
    echo Please install Maven 3.8 or higher
    pause
    exit /b 1
)

echo [INFO] Maven found:
mvn -version
echo.

echo ============================================================
echo   Building the application...
echo ============================================================
call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo [ERROR] Build failed!
    pause
    exit /b 1
)

echo.
echo ============================================================
echo   Starting the application...
echo ============================================================
echo.
echo Application will be available at:
echo   - API: http://localhost:8080/api
echo   - Swagger UI: http://localhost:8080/swagger-ui.html
echo   - H2 Console: http://localhost:8080/h2-console
echo.
echo Test accounts:
echo   - Admin:  admin@maternal.com  / admin123
echo   - Doctor: doctor@maternal.com / doctor123
echo   - User:   user@maternal.com   / user123
echo.
echo ============================================================

call mvn spring-boot:run
