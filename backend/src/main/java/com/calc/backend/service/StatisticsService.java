package com.calc.backend.service;

import com.calc.backend.dto.StatisticsDto;
import com.calc.backend.repository.MathTaskRepository;
import com.calc.backend.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final MathTaskRepository taskRepository;
    private final UserUtil userUtil;

    public StatisticsDto getStatistics() {
        var user = userUtil.getCurrentUser();
        int total = taskRepository.countByUser(user);
        int correct = taskRepository.countByUserAndCorrectTrue(user);

        double accuracy = total == 0 ? 0.0 : (correct * 100.0 / total);

        return new StatisticsDto(total, correct, accuracy);
    }
}
