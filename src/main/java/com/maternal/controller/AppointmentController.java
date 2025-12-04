package com.maternal.controller;

import com.maternal.dto.ApiResponse;
import com.maternal.dto.AppointmentDTO;
import com.maternal.dto.AppointmentRequest;
import com.maternal.service.AppointmentService;
import com.maternal.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for appointment management endpoints
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AuthService authService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<AppointmentDTO>> createAppointment(
            @Valid @RequestBody AppointmentRequest request
    ) {
        try {
            var currentUser = authService.getCurrentUser();
            AppointmentDTO appointment = appointmentService.createAppointment(currentUser.getId(), request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Appointment created successfully", appointment));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<AppointmentDTO>> getAppointmentById(@PathVariable Long id) {
        try {
            AppointmentDTO appointment = appointmentService.getAppointmentById(id);
            return ResponseEntity.ok(ApiResponse.success(appointment));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/my-appointments")
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getMyAppointments() {
        var currentUser = authService.getCurrentUser();
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByUserId(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(appointments));
    }

    @GetMapping("/upcoming")
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getUpcomingAppointments() {
        var currentUser = authService.getCurrentUser();
        List<AppointmentDTO> appointments = appointmentService.getUpcomingAppointments(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(appointments));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAppointmentsByUserId(@PathVariable Long userId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(appointments));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<AppointmentDTO>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequest request
    ) {
        try {
            AppointmentDTO appointment = appointmentService.updateAppointment(id, request);
            return ResponseEntity.ok(ApiResponse.success("Appointment updated successfully", appointment));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.ok(ApiResponse.success("Appointment deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
