package com.maternal.repository;

import com.maternal.model.NutritionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for NutritionRecord entity
 */
@Repository
public interface NutritionRecordRepository extends JpaRepository<NutritionRecord, Long> {

    /**
     * Find nutrition records by baby
     */
    List<NutritionRecord> findByBabyIdOrderByRecordDateDesc(Long babyId);

    /**
     * Find nutrition records by date
     */
    List<NutritionRecord> findByBabyIdAndRecordDate(Long babyId, LocalDate date);

    /**
     * Find nutrition records by feeding type
     */
    List<NutritionRecord> findByBabyIdAndFeedingType(Long babyId, String feedingType);

    /**
     * Find nutrition records in date range
     */
    @Query("SELECT n FROM NutritionRecord n WHERE n.baby.id = :babyId " +
           "AND n.recordDate BETWEEN :startDate AND :endDate " +
           "ORDER BY n.recordDate DESC")
    List<NutritionRecord> findByBabyIdAndDateRange(Long babyId, LocalDate startDate, LocalDate endDate);

    /**
     * Get nutrition records count for baby
     */
    long countByBabyId(Long babyId);
}
