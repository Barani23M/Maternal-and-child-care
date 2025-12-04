package com.maternal.controller;

import com.maternal.dto.ApiResponse;
import com.maternal.dto.BabyDTO;
import com.maternal.dto.BabyRequest;
import com.maternal.service.AuthService;
import com.maternal.service.BabyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for baby management endpoints
 */
@RestController
@RequestMapping("/api/babies")
@RequiredArgsConstructor
public class BabyController {

    private final BabyService babyService;
    private final AuthService authService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<BabyDTO>> createBaby(@Valid @RequestBody BabyRequest request) {
        try {
            var currentUser = authService.getCurrentUser();
            BabyDTO baby = babyService.createBaby(currentUser.getId(), request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Baby profile created successfully", baby));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<BabyDTO>> getBabyById(@PathVariable Long id) {
        try {
            BabyDTO baby = babyService.getBabyById(id);
            return ResponseEntity.ok(ApiResponse.success(baby));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/unique/{uniqueId}")
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<BabyDTO>> getBabyByUniqueId(@PathVariable String uniqueId) {
        try {
            BabyDTO baby = babyService.getBabyByUniqueId(uniqueId);
            return ResponseEntity.ok(ApiResponse.success(baby));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/parent/{parentId}")
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<BabyDTO>>> getBabiesByParentId(@PathVariable Long parentId) {
        List<BabyDTO> babies = babyService.getBabiesByParentId(parentId);
        return ResponseEntity.ok(ApiResponse.success(babies));
    }

    @GetMapping("/my-babies")
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<BabyDTO>>> getMyBabies() {
        var currentUser = authService.getCurrentUser();
        List<BabyDTO> babies = babyService.getBabiesByParentId(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(babies));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<BabyDTO>> updateBaby(
            @PathVariable Long id,
            @Valid @RequestBody BabyRequest request
    ) {
        try {
            BabyDTO baby = babyService.updateBaby(id, request);
            return ResponseEntity.ok(ApiResponse.success("Baby profile updated successfully", baby));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteBaby(@PathVariable Long id) {
        try {
            babyService.deleteBaby(id);
            return ResponseEntity.ok(ApiResponse.success("Baby profile deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
