package com.maternal.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Baby entity for tracking child health information
 */
@Entity
@Table(name = "babies", indexes = {
    @Index(name = "idx_baby_birth_date", columnList = "birth_date"),
    @Index(name = "idx_baby_parent", columnList = "parent_id"),
    @Index(name = "idx_baby_unique_id", columnList = "unique_id", unique = true)
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Baby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(name = "weight_at_birth")
    private Double weightAtBirth; // in kg

    @Column(name = "height_at_birth")
    private Double heightAtBirth; // in cm

    @Column(name = "blood_type", length = 5)
    private String bloodType;

    @Column(name = "unique_id", unique = true, nullable = false, length = 50)
    private String uniqueId;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User parent;

    @OneToMany(mappedBy = "baby", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Vaccination> vaccinations = new ArrayList<>();

    @OneToMany(mappedBy = "baby", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<GrowthRecord> growthRecords = new ArrayList<>();

    @OneToMany(mappedBy = "baby", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<NutritionRecord> nutritionRecords = new ArrayList<>();

    @OneToMany(mappedBy = "baby", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Appointment> appointments = new ArrayList<>();

    // Business methods
    public long getAgeInDays() {
        if (birthDate != null) {
            return ChronoUnit.DAYS.between(birthDate, LocalDate.now());
        }
        return 0;
    }

    public long getAgeInMonths() {
        return getAgeInDays() / 30;
    }

    public Map<String, Object> getVaccinationProgress() {
        int total = vaccinations != null ? vaccinations.size() : 0;
        long completed = vaccinations != null ? 
            vaccinations.stream().filter(v -> "COMPLETED".equals(v.getStatus())).count() : 0;
        
        Map<String, Object> progress = new HashMap<>();
        progress.put("total", total);
        progress.put("completed", completed);
        progress.put("percentage", total > 0 ? (completed * 100.0 / total) : 0);
        return progress;
    }

    /**
     * Generate a unique ID for baby
     */
    public static String generateUniqueId() {
        int year = LocalDate.now().getYear();
        String uniquePart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return String.format("BABY-%d-%s", year, uniquePart);
    }
}
