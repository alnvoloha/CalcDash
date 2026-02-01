package com.calc.backend.service;

import com.calc.backend.dto.TrainingSettingsDto;
import com.calc.backend.entity.TrainingSettings;
import com.calc.backend.entity.User;
import com.calc.backend.repository.TrainingSettingsRepository;
import com.calc.backend.util.TrainingSettingsMapper;
import com.calc.backend.util.UserUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingSettingsService {

    private final TrainingSettingsRepository repository;
    private final UserUtil userUtil;

    public TrainingSettingsService(TrainingSettingsRepository repository, UserUtil userUtil) {
        this.repository = repository;
        this.userUtil = userUtil;
    }

    public TrainingSettingsDto save(TrainingSettingsDto dto) {
        User user = userUtil.getCurrentUser();
        TrainingSettings entity = TrainingSettingsMapper.toEntity(dto, user);
        repository.save(entity);
        return TrainingSettingsMapper.toDto(entity);
    }

    public Optional<TrainingSettingsDto> getCurrentUserSettings() {
        User user = userUtil.getCurrentUser();
        return repository.findByUser(user)
                .map(TrainingSettingsMapper::toDto);
    }
}
