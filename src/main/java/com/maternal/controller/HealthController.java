package com.maternal.controller;

import com.maternal.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Health check and system info controller
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "Maternal Care Backend");
        health.put("version", "1.0.0");
        
        return ResponseEntity.ok(ApiResponse.success("Service is running", health));
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<Map<String, Object>>> info() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "Maternal and Child Health Care System");
        info.put("description", "REST API for pregnancy and child health monitoring");
        info.put("version", "1.0.0");
        info.put("apiDocs", "/swagger-ui.html");
        info.put("endpoints", Map.of(
            "auth", "/api/auth",
            "users", "/api/users",
            "babies", "/api/babies",
            "appointments", "/api/appointments"
        ));
        
        return ResponseEntity.ok(ApiResponse.success(info));
    }
}
