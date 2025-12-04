package com.maternal.service;

import com.maternal.dto.BabyDTO;
import com.maternal.dto.BabyRequest;
import com.maternal.model.Baby;
import com.maternal.model.User;
import com.maternal.repository.BabyRepository;
import com.maternal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for baby management operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BabyService {

    private final BabyRepository babyRepository;
    private final UserRepository userRepository;

    @Transactional
    public BabyDTO createBaby(Long parentId, BabyRequest request) {
        User parent = userRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found with id: " + parentId));

        Baby baby = Baby.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .gender(request.getGender())
                .weightAtBirth(request.getWeightAtBirth())
                .heightAtBirth(request.getHeightAtBirth())
                .bloodType(request.getBloodType())
                .uniqueId(Baby.generateUniqueId())
                .photoUrl(request.getPhotoUrl())
                .notes(request.getNotes())
                .parent(parent)
                .isActive(true)
                .build();

        baby = babyRepository.save(baby);
        log.info("New baby created: {} for parent: {}", baby.getName(), parent.getEmail());
        return mapToDTO(baby);
    }

    @Transactional(readOnly = true)
    public BabyDTO getBabyById(Long id) {
        Baby baby = babyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Baby not found with id: " + id));
        return mapToDTO(baby);
    }

    @Transactional(readOnly = true)
    public BabyDTO getBabyByUniqueId(String uniqueId) {
        Baby baby = babyRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new RuntimeException("Baby not found with unique ID: " + uniqueId));
        return mapToDTO(baby);
    }

    @Transactional(readOnly = true)
    public List<BabyDTO> getBabiesByParentId(Long parentId) {
        return babyRepository.findByParentIdAndIsActiveTrue(parentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BabyDTO updateBaby(Long id, BabyRequest request) {
        Baby baby = babyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Baby not found with id: " + id));

        baby.setName(request.getName());
        baby.setBirthDate(request.getBirthDate());
        baby.setGender(request.getGender());
        baby.setWeightAtBirth(request.getWeightAtBirth());
        baby.setHeightAtBirth(request.getHeightAtBirth());
        baby.setBloodType(request.getBloodType());
        baby.setPhotoUrl(request.getPhotoUrl());
        baby.setNotes(request.getNotes());

        baby = babyRepository.save(baby);
        log.info("Baby updated: {}", baby.getName());
        return mapToDTO(baby);
    }

    @Transactional
    public void deleteBaby(Long id) {
        Baby baby = babyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Baby not found with id: " + id));
        baby.setIsActive(false);
        babyRepository.save(baby);
        log.info("Baby deactivated: {}", baby.getName());
    }

    private BabyDTO mapToDTO(Baby baby) {
        return BabyDTO.builder()
                .id(baby.getId())
                .name(baby.getName())
                .birthDate(baby.getBirthDate())
                .gender(baby.getGender())
                .weightAtBirth(baby.getWeightAtBirth())
                .heightAtBirth(baby.getHeightAtBirth())
                .bloodType(baby.getBloodType())
                .parentId(baby.getParent().getId())
                .uniqueId(baby.getUniqueId())
                .photoUrl(baby.getPhotoUrl())
                .notes(baby.getNotes())
                .isActive(baby.getIsActive())
                .createdAt(baby.getCreatedAt())
                .updatedAt(baby.getUpdatedAt())
                .ageInDays(baby.getAgeInDays())
                .ageInMonths(baby.getAgeInMonths())
                .vaccinationProgress(baby.getVaccinationProgress())
                .build();
    }
}
