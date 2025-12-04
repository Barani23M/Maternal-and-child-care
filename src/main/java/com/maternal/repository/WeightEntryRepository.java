package com.maternal.repository;

import com.maternal.model.WeightEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for WeightEntry entity
 */
@Repository
public interface WeightEntryRepository extends JpaRepository<WeightEntry, Long> {

    /**
     * Find weight entries by user
     */
    List<WeightEntry> findByUserIdOrderByDateDesc(Long userId);

    /**
     * Find weight entries in date range
     */
    @Query("SELECT w FROM WeightEntry w WHERE w.user.id = :userId " +
           "AND w.date BETWEEN :startDate AND :endDate ORDER BY w.date ASC")
    List<WeightEntry> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    /**
     * Find latest weight entry for user
     */
    Optional<WeightEntry> findFirstByUserIdOrderByDateDesc(Long userId);

    /**
     * Find weight entries by pregnancy week
     */
    List<WeightEntry> findByUserIdAndPregnancyWeek(Long userId, Integer week);

    /**
     * Get weight entry count for user
     */
    long countByUserId(Long userId);
}
