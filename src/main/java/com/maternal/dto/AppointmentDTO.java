package com.maternal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Appointment entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {

    private Long id;
    private Long userId;
    private Long babyId;
    private String appointmentType;
    private LocalDateTime appointmentDate;
    private String doctorName;
    private String clinicName;
    private String clinicAddress;
    private String purpose;
    private String status;
    private String notes;
    private Boolean reminderSent;
    private Boolean isUpcoming;
    private Boolean isOverdue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
