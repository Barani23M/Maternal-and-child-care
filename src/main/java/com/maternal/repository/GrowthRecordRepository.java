package com.maternal.repository;

import com.maternal.model.GrowthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for GrowthRecord entity
 */
@Repository
public interface GrowthRecordRepository extends JpaRepository<GrowthRecord, Long> {

    /**
     * Find growth records by baby
     */
    List<GrowthRecord> findByBabyIdOrderByRecordDateDesc(Long babyId);

    /**
     * Find latest growth record
     */
    Optional<GrowthRecord> findFirstByBabyIdOrderByRecordDateDesc(Long babyId);

    /**
     * Find growth records by age range
     */
    @Query("SELECT g FROM GrowthRecord g WHERE g.baby.id = :babyId " +
           "AND g.ageInMonths BETWEEN :startMonth AND :endMonth " +
           "ORDER BY g.recordDate ASC")
    List<GrowthRecord> findByBabyIdAndAgeRange(Long babyId, Integer startMonth, Integer endMonth);

    /**
     * Get growth records count for baby
     */
    long countByBabyId(Long babyId);
}
