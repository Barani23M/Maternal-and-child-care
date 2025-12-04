package com.maternal.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Weight entry entity for tracking pregnancy weight
 */
@Entity
@Table(name = "weight_entries", indexes = {
    @Index(name = "idx_weight_user", columnList = "user_id"),
    @Index(name = "idx_weight_date", columnList = "date")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeightEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Double weight; // Current weight in kg

    @Column(name = "pregnancy_week", nullable = false)
    private Integer pregnancyWeek; // Week of pregnancy (4-40)

    @Column(name = "pre_pregnancy_weight")
    private Double prePregnancyWeight; // Pre-pregnancy weight in kg

    @Column
    private Double height; // Height in cm

    @Column
    private Double bmi; // Calculated BMI

    @Column(name = "weight_gain")
    private Double weightGain; // Total weight gain since pre-pregnancy

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    // Business methods
    public Double calculateBmi() {
        if (prePregnancyWeight != null && height != null && height > 0) {
            double heightM = height / 100.0;
            this.bmi = Math.round(prePregnancyWeight / (heightM * heightM) * 10.0) / 10.0;
            return this.bmi;
        }
        return null;
    }

    public Double calculateWeightGain() {
        if (prePregnancyWeight != null) {
            this.weightGain = Math.round((weight - prePregnancyWeight) * 10.0) / 10.0;
            return this.weightGain;
        }
        return null;
    }

    public String getBmiCategory() {
        if (bmi == null) return "UNKNOWN";
        if (bmi < 18.5) return "UNDERWEIGHT";
        if (bmi < 25) return "NORMAL";
        if (bmi < 30) return "OVERWEIGHT";
        return "OBESE";
    }

    public String getStatus() {
        if (weightGain == null || bmi == null) return "UNKNOWN";

        String category = getBmiCategory();
        int week = pregnancyWeek;

        // First trimester (weeks 1-13)
        if (week <= 13) {
            if (weightGain < 0.5) return "LOW";
            if (weightGain > 2.0) return "HIGH";
            return "GOOD";
        }

        // Second and third trimester calculations
        int weeksSinceFirstTrimester = week - 13;
        double expectedMin, expectedMax;

        switch (category) {
            case "UNDERWEIGHT":
                expectedMin = 0.5 + (weeksSinceFirstTrimester * 0.44);
                expectedMax = 2.0 + (weeksSinceFirstTrimester * 0.58);
                break;
            case "NORMAL":
                expectedMin = 0.5 + (weeksSinceFirstTrimester * 0.35);
                expectedMax = 2.0 + (weeksSinceFirstTrimester * 0.50);
                break;
            case "OVERWEIGHT":
                expectedMin = 0.5 + (weeksSinceFirstTrimester * 0.23);
                expectedMax = 2.0 + (weeksSinceFirstTrimester * 0.33);
                break;
            default: // OBESE
                expectedMin = 0.5 + (weeksSinceFirstTrimester * 0.17);
                expectedMax = 2.0 + (weeksSinceFirstTrimester * 0.27);
                break;
        }

        if (weightGain < expectedMin) return "LOW";
        if (weightGain > expectedMax) return "HIGH";
        return "GOOD";
    }
}
