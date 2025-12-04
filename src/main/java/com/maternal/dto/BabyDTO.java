package com.maternal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for Baby entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BabyDTO {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private String gender;
    private Double weightAtBirth;
    private Double heightAtBirth;
    private String bloodType;
    private Long parentId;
    private String uniqueId;
    private String photoUrl;
    private String notes;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long ageInDays;
    private Long ageInMonths;
    private Map<String, Object> vaccinationProgress;
}
