package com.maternal.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Nutrition record entity for tracking baby nutrition
 */
@Entity
@Table(name = "nutrition_records", indexes = {
    @Index(name = "idx_nutrition_baby", columnList = "baby_id"),
    @Index(name = "idx_nutrition_date", columnList = "record_date")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(name = "feeding_type", nullable = false, length = 50)
    private String feedingType; // BREASTFEEDING, FORMULA, SOLID_FOOD, MIXED

    @Column(name = "meal_type", length = 50)
    private String mealType; // BREAKFAST, LUNCH, DINNER, SNACK

    @Column(columnDefinition = "TEXT")
    private String foodItems;

    @Column
    private Integer quantity; // in ml for liquids

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
