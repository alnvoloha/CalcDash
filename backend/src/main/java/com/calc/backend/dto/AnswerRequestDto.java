package com.calc.backend.dto;

import java.util.UUID;

public class AnswerRequestDto {
    private UUID taskId;
    private double userAnswer;

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public double getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(double userAnswer) {
        this.userAnswer = userAnswer;
    }
}
