# Maternal and Child Care Application

A comprehensive healthcare management system built with Java Spring Boot for managing maternal and child healthcare services.

## Features

- **User Management**: Role-based access control (PATIENT, DOCTOR, ADMIN)
- **Authentication**: Secure JWT-based authentication
- **Appointment Management**: Schedule and manage medical appointments
- **Baby Care**: Track baby growth, nutrition, and vaccination records
- **Health Monitoring**: Weight tracking and growth records
- **API Documentation**: Interactive Swagger/OpenAPI documentation

## Technology Stack

- **Backend**: Java 17, Spring Boot 3.2.0
- **Security**: Spring Security with JWT
- **Database**: H2 (development), configurable for production
- **API Documentation**: Springdoc OpenAPI (Swagger)
- **Build Tool**: Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

### Running the Application

**Windows:**
```bash
run.bat
```

**Linux/Mac:**
```bash
chmod +x run.sh
./run.sh
```

The application will start on `http://localhost:8080`

### Building the Application

```bash
mvn clean install
```

### Running Tests

```bash
mvn test
```

## API Documentation

Once the application is running, access the interactive API documentation at:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/v3/api-docs`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login

### Users
- `GET /api/users/profile` - Get current user profile
- `PUT /api/users/profile` - Update user profile
- `GET /api/users` - Get all users (Admin only)

### Appointments
- `GET /api/appointments` - Get user appointments
- `POST /api/appointments` - Create new appointment
- `PUT /api/appointments/{id}` - Update appointment
- `DELETE /api/appointments/{id}` - Cancel appointment

### Baby Care
- `GET /api/babies` - Get all babies for current user
- `POST /api/babies` - Register new baby
- `POST /api/babies/{id}/growth` - Add growth record
- `POST /api/babies/{id}/weight` - Add weight entry
- `POST /api/babies/{id}/nutrition` - Add nutrition record
- `POST /api/babies/{id}/vaccination` - Add vaccination record

### Health
- `GET /api/health` - Health check endpoint

## Configuration

Configuration files are located in `src/main/resources/`:

- `application.properties` - Development configuration
- `application-prod.properties` - Production configuration

### Key Configuration Properties

```properties
# Server Port
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:h2:file:./data/maternal_care
spring.datasource.username=admin
spring.datasource.password=admin123

# JWT Configuration
jwt.secret=your-secret-key
jwt.expiration=86400000
```

## Project Structure

```
src/
├── main/
│   ├── java/com/maternal/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST Controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exception/       # Exception handlers
│   │   ├── model/           # Entity models
│   │   ├── repository/      # JPA Repositories
│   │   ├── security/        # Security configuration
│   │   └── service/         # Business logic services
│   └── resources/
│       ├── application.properties
│       └── application-prod.properties
```

## Default Users

The application initializes with the following test users:

- **Admin**: admin@test.com / admin123
- **Doctor**: doctor@test.com / doctor123
- **Patient**: patient@test.com / patient123

## Security

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control
- CORS configuration for frontend integration

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Contact

For any queries or support, please contact the development team.
