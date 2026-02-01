package com.calc.backend.service;

import com.calc.backend.entity.MathTask;
import com.calc.backend.entity.TrainingSettings;
import com.calc.backend.entity.User;
import com.calc.backend.enums.TaskType;
import com.calc.backend.generator.TaskGenerationManager;
import com.calc.backend.repository.MathTaskRepository;
import com.calc.backend.repository.TrainingSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;


import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskGenerationManager generationManager;
    private final TrainingSettingsRepository settingsRepository;
    private final MathTaskRepository taskRepository;

    public List<MathTask> generateAndSaveTasks(User user, TaskType type) {
        TrainingSettings settings = settingsRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Не найдены настройки пользователя"));

        List<String> operations = Arrays.asList(settings.getOperations().split(""));
        int count = settings.getNumberOfExamples();
        double min = settings.getMinOperand();
        double max = settings.getMaxOperand();

        List<MathTask> tasks = generationManager.generateTasks(
                operations,
                count,
                min,
                max,
                mapDifficulty(settings.getDifficulty()),
                type,
                user
        );

        return taskRepository.saveAll(tasks);
    }

    private com.calc.backend.enums.TaskDifficulty mapDifficulty(String difficultyRaw) {
        return switch (difficultyRaw.toLowerCase()) {
            case "easy" -> com.calc.backend.enums.TaskDifficulty.EASY;
            case "medium" -> com.calc.backend.enums.TaskDifficulty.MEDIUM;
            case "hard" -> com.calc.backend.enums.TaskDifficulty.HARD;
            case "extreme" -> com.calc.backend.enums.TaskDifficulty.EXTREME;
            default -> throw new IllegalArgumentException("Неизвестный уровень сложности: " + difficultyRaw);
        };
    }

    public boolean submitAnswer(UUID taskId, double userAnswer) {
        MathTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));

        boolean isCorrect = Math.abs(task.getAnswer() - userAnswer) < 0.0001;

        task.setUserAnswer(userAnswer);
        task.setCorrect(isCorrect);

        taskRepository.save(task);

        return isCorrect;
    }
    public List<MathTask> getIncorrectTasks(User user) {
        return taskRepository.findByUserAndCorrectFalse(user);
    }

}
