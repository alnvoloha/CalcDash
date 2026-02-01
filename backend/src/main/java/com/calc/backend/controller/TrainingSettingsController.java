package com.calc.backend.controller;

import com.calc.backend.dto.TrainingSettingsDto;
import com.calc.backend.service.TrainingSettingsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class TrainingSettingsController {

    private final TrainingSettingsService service;

    public TrainingSettingsController(TrainingSettingsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TrainingSettingsDto> saveSettings(@RequestBody @Valid TrainingSettingsDto dto) {
        TrainingSettingsDto saved = service.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<?> getSettings() {
        return service.getCurrentUserSettings()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
