package com.maternal.repository;

import com.maternal.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Appointment entity
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Find appointments by user
     */
    List<Appointment> findByUserIdOrderByAppointmentDateDesc(Long userId);

    /**
     * Find appointments by baby
     */
    List<Appointment> findByBabyIdOrderByAppointmentDateDesc(Long babyId);

    /**
     * Find appointments by status
     */
    List<Appointment> findByStatus(Appointment.AppointmentStatus status);

    /**
     * Find upcoming appointments for user
     */
    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId " +
           "AND a.appointmentDate > :now AND a.status = 'SCHEDULED' " +
           "ORDER BY a.appointmentDate ASC")
    List<Appointment> findUpcomingAppointmentsByUserId(Long userId, LocalDateTime now);

    /**
     * Find overdue appointments
     */
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate < :now " +
           "AND a.status = 'SCHEDULED' ORDER BY a.appointmentDate ASC")
    List<Appointment> findOverdueAppointments(LocalDateTime now);

    /**
     * Find appointments in date range
     */
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate BETWEEN :startDate AND :endDate " +
           "ORDER BY a.appointmentDate ASC")
    List<Appointment> findAppointmentsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find appointments needing reminders
     */
    @Query("SELECT a FROM Appointment a WHERE a.reminderSent = false " +
           "AND a.status = 'SCHEDULED' AND a.appointmentDate BETWEEN :now AND :reminderWindow")
    List<Appointment> findAppointmentsNeedingReminder(LocalDateTime now, LocalDateTime reminderWindow);
}
