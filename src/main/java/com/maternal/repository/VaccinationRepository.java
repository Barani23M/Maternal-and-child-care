package com.maternal.repository;

import com.maternal.model.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Vaccination entity
 */
@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {

    /**
     * Find vaccinations by baby
     */
    List<Vaccination> findByBabyIdOrderByScheduledDateAsc(Long babyId);

    /**
     * Find vaccinations by status
     */
    List<Vaccination> findByBabyIdAndStatus(Long babyId, String status);

    /**
     * Find upcoming vaccinations
     */
    @Query("SELECT v FROM Vaccination v WHERE v.baby.id = :babyId " +
           "AND v.scheduledDate >= :today AND v.status = 'SCHEDULED' " +
           "ORDER BY v.scheduledDate ASC")
    List<Vaccination> findUpcomingVaccinations(Long babyId, LocalDate today);

    /**
     * Find overdue vaccinations
     */
    @Query("SELECT v FROM Vaccination v WHERE v.baby.id = :babyId " +
           "AND v.scheduledDate < :today AND v.status = 'SCHEDULED'")
    List<Vaccination> findOverdueVaccinations(Long babyId, LocalDate today);

    /**
     * Get completed vaccinations count
     */
    long countByBabyIdAndStatus(Long babyId, String status);
}
