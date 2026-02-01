package com.calc.backend.util;

import com.calc.backend.dto.TrainingSettingsDto;
import com.calc.backend.entity.TrainingSettings;
import com.calc.backend.entity.User;

public class TrainingSettingsMapper {

    public static TrainingSettings toEntity(TrainingSettingsDto dto, User user) {
        return TrainingSettings.builder()
                .difficulty(dto.getDifficulty())
                .numberOfExamples(dto.getNumberOfExamples())
                .operations(dto.getOperations())
                .minOperand(dto.getMinOperand())
                .maxOperand(dto.getMaxOperand())
                .user(user)
                .build();
    }

    public static TrainingSettingsDto toDto(TrainingSettings entity) {
        return TrainingSettingsDto.builder()
                .difficulty(entity.getDifficulty())
                .numberOfExamples(entity.getNumberOfExamples())
                .operations(entity.getOperations())
                .minOperand(entity.getMinOperand())
                .maxOperand(entity.getMaxOperand())
                .build();
    }
}
