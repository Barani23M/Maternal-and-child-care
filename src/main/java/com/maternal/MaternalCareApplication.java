package com.maternal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for Maternal and Child Health Care Monitoring System
 * 
 * @author Maternal Care Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class MaternalCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaternalCareApplication.class, args);
        System.out.println("=" .repeat(60));
        System.out.println("🍼 PREGNANCY BABY CARE SYSTEM - Spring Boot Backend");
        System.out.println("=" .repeat(60));
        System.out.println("✅ Application started successfully!");
        System.out.println("🌐 API available at: http://localhost:8080");
        System.out.println("📚 API Documentation: http://localhost:8080/swagger-ui.html");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
        System.out.println("=" .repeat(60));
    }
}
