package com.maternal.repository;

import com.maternal.model.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Baby entity
 */
@Repository
public interface BabyRepository extends JpaRepository<Baby, Long> {

    /**
     * Find baby by unique ID
     */
    Optional<Baby> findByUniqueId(String uniqueId);

    /**
     * Check if unique ID exists
     */
    boolean existsByUniqueId(String uniqueId);

    /**
     * Find all babies for a parent
     */
    List<Baby> findByParentId(Long parentId);

    /**
     * Find active babies for a parent
     */
    List<Baby> findByParentIdAndIsActiveTrue(Long parentId);

    /**
     * Find all active babies
     */
    List<Baby> findByIsActiveTrue();

    /**
     * Search babies by name
     */
    @Query("SELECT b FROM Baby b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Baby> searchByName(String name);

    /**
     * Get babies count for a parent
     */
    long countByParentId(Long parentId);
}
