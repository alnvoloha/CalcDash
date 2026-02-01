package com.calc.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsDto {
    private int totalTasks;
    private int correctAnswers;
    private double accuracy;
}
