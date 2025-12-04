package com.maternal.config;

import com.maternal.model.User;
import com.maternal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Data initialization for development environment
 * Creates sample users for testing
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    @Profile("development")
    public CommandLineRunner initData() {
        return args -> {
            // Check if data already exists
            if (userRepository.count() > 0) {
                log.info("Database already contains data. Skipping initialization.");
                return;
            }

            log.info("Initializing sample data...");

            // Create admin user
            User admin = User.builder()
                    .fullName("Admin User")
                    .email("admin@maternal.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(User.Role.ADMIN)
                    .phone("1234567890")
                    .isActive(true)
                    .emailVerified(true)
                    .build();
            userRepository.save(admin);
            log.info("Created admin user: admin@maternal.com / admin123");

            // Create doctor user
            User doctor = User.builder()
                    .fullName("Dr. Sarah Johnson")
                    .email("doctor@maternal.com")
                    .password(passwordEncoder.encode("doctor123"))
                    .role(User.Role.DOCTOR)
                    .phone("0987654321")
                    .isActive(true)
                    .emailVerified(true)
                    .build();
            userRepository.save(doctor);
            log.info("Created doctor user: doctor@maternal.com / doctor123");

            // Create regular user
            User user = User.builder()
                    .fullName("Jane Doe")
                    .email("user@maternal.com")
                    .password(passwordEncoder.encode("user123"))
                    .role(User.Role.USER)
                    .phone("5555555555")
                    .isActive(true)
                    .emailVerified(true)
                    .build();
            userRepository.save(user);
            log.info("Created regular user: user@maternal.com / user123");

            log.info("Sample data initialization completed!");
            log.info("=" .repeat(60));
            log.info("Test Accounts:");
            log.info("  Admin:  admin@maternal.com  / admin123");
            log.info("  Doctor: doctor@maternal.com / doctor123");
            log.info("  User:   user@maternal.com   / user123");
            log.info("=" .repeat(60));
        };
    }
}
