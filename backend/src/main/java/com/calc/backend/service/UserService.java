package com.calc.backend.service;

import com.calc.backend.dto.StatisticsDto;
import com.calc.backend.dto.TrainingSettingsDto;
import com.calc.backend.dto.UserProfileDto;
import com.calc.backend.entity.TrainingSettings;
import com.calc.backend.entity.User;
import com.calc.backend.util.TrainingSettingsMapper;

import com.calc.backend.repository.TrainingSettingsRepository;
import com.calc.backend.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserUtil userUtil;
    private final TrainingSettingsRepository settingsRepository;
    private final StatisticsService statisticsService;

    public UserProfileDto getProfile() {
        User user = userUtil.getCurrentUser();

        TrainingSettings settings = settingsRepository.findByUser(user)
                .orElse(TrainingSettings.builder()
                        .difficulty("EASY")
                        .numberOfExamples(10)
                        .operations("+")
                        .minOperand(1)
                        .maxOperand(10)
                        .user(user)
                        .build());

        TrainingSettingsDto settingsDto = TrainingSettingsMapper.toDto(settings);
        StatisticsDto statsDto = statisticsService.getStatistics();

        return UserProfileDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .trainingSettings(settingsDto)
                .statistics(statsDto)
                .build();
    }
}
