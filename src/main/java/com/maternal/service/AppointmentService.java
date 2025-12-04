package com.maternal.service;

import com.maternal.dto.AppointmentDTO;
import com.maternal.dto.AppointmentRequest;
import com.maternal.model.Appointment;
import com.maternal.model.Baby;
import com.maternal.model.User;
import com.maternal.repository.AppointmentRepository;
import com.maternal.repository.BabyRepository;
import com.maternal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for appointment management operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final BabyRepository babyRepository;

    @Transactional
    public AppointmentDTO createAppointment(Long userId, AppointmentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Appointment appointment = Appointment.builder()
                .appointmentType(request.getAppointmentType())
                .appointmentDate(request.getAppointmentDate())
                .doctorName(request.getDoctorName())
                .clinicName(request.getClinicName())
                .clinicAddress(request.getClinicAddress())
                .purpose(request.getPurpose())
                .status(Appointment.AppointmentStatus.SCHEDULED)
                .notes(request.getNotes())
                .user(user)
                .build();

        if (request.getBabyId() != null) {
            Baby baby = babyRepository.findById(request.getBabyId())
                    .orElseThrow(() -> new RuntimeException("Baby not found with id: " + request.getBabyId()));
            appointment.setBaby(baby);
        }

        appointment = appointmentRepository.save(appointment);
        log.info("Appointment created for user: {}", user.getEmail());
        return mapToDTO(appointment);
    }

    @Transactional(readOnly = true)
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        return mapToDTO(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByUserId(Long userId) {
        return appointmentRepository.findByUserIdOrderByAppointmentDateDesc(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentDTO> getUpcomingAppointments(Long userId) {
        return appointmentRepository.findUpcomingAppointmentsByUserId(userId, LocalDateTime.now()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AppointmentDTO updateAppointment(Long id, AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        appointment.setAppointmentType(request.getAppointmentType());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setDoctorName(request.getDoctorName());
        appointment.setClinicName(request.getClinicName());
        appointment.setClinicAddress(request.getClinicAddress());
        appointment.setPurpose(request.getPurpose());
        appointment.setNotes(request.getNotes());

        if (request.getStatus() != null) {
            appointment.setStatus(Appointment.AppointmentStatus.valueOf(request.getStatus().toUpperCase()));
        }

        appointment = appointmentRepository.save(appointment);
        log.info("Appointment updated: {}", appointment.getId());
        return mapToDTO(appointment);
    }

    @Transactional
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
        log.info("Appointment deleted: {}", id);
    }

    private AppointmentDTO mapToDTO(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .userId(appointment.getUser().getId())
                .babyId(appointment.getBaby() != null ? appointment.getBaby().getId() : null)
                .appointmentType(appointment.getAppointmentType())
                .appointmentDate(appointment.getAppointmentDate())
                .doctorName(appointment.getDoctorName())
                .clinicName(appointment.getClinicName())
                .clinicAddress(appointment.getClinicAddress())
                .purpose(appointment.getPurpose())
                .status(appointment.getStatus().name())
                .notes(appointment.getNotes())
                .reminderSent(appointment.getReminderSent())
                .isUpcoming(appointment.isUpcoming())
                .isOverdue(appointment.isOverdue())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }
}
