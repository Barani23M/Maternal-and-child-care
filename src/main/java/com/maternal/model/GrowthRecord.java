package com.maternal.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Growth record entity for tracking baby growth metrics
 */
@Entity
@Table(name = "growth_records", indexes = {
    @Index(name = "idx_growth_baby", columnList = "baby_id"),
    @Index(name = "idx_growth_date", columnList = "record_date")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrowthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false)
    private Double weight; // in kg

    @Column(nullable = false)
    private Double height; // in cm

    @Column(name = "head_circumference")
    private Double headCircumference; // in cm

    @Column(name = "age_in_months")
    private Integer ageInMonths;

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
    @JoinColumn(name = "baby_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Baby baby;
}
