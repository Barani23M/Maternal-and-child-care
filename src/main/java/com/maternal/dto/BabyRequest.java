package com.maternal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for Baby creation/update requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BabyRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @NotBlank(message = "Gender is required")
    private String gender;

    private Double weightAtBirth;
    private Double heightAtBirth;
    private String bloodType;
    private String photoUrl;
    private String notes;
}
