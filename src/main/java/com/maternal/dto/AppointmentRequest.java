package com.maternal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Appointment creation/update requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {

    @NotBlank(message = "Appointment type is required")
    private String appointmentType;

    @NotNull(message = "Appointment date is required")
    private LocalDateTime appointmentDate;

    @NotBlank(message = "Doctor name is required")
    private String doctorName;

    private String clinicName;
    private String clinicAddress;
    private String purpose;
    private String status;
    private String notes;
    private Long babyId;
}
