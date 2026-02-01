package com.calc.backend.service;

import com.calc.backend.entity.MathTask;
import com.calc.backend.entity.User;
import com.calc.backend.enums.TaskDifficulty;
import com.calc.backend.enums.TaskType;
import com.calc.backend.repository.MathTaskRepository;
import com.calc.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MarathonService {

    private final MathTaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<String, Object> startMarathon(User user, TaskDifficulty difficulty) {
        System.out.println("⚙️ Генерация марафона для " + user.getUsername() +
                ", сложность: " + difficulty);

        List<MathTask> tasks = new ArrayList<>();
        int totalTasks = 50; // Вернул 50, как указано в комментарии

        for (int i = 0; i < totalTasks; i++) {
            try {
                MathTask task = generateNonNegativeTask(user, difficulty);
                tasks.add(task);
            } catch (Exception e) {
                System.out.println("❌ Ошибка генерации задачи " + i + ": " + e);
            }
        }

        taskRepository.saveAll(tasks);
        System.out.println("Сохранено задач: " + tasks.size());

        int timePerTask = switch (difficulty) {
            case EASY -> 10;
            case MEDIUM -> 7;
            case HARD -> 5;
            case EXTREME -> 3;
        };

        Map<String, Object> result = new HashMap<>();
        result.put("tasks", tasks);
        result.put("timePerTask", timePerTask);
        return result;
    }

    private MathTask generateNonNegativeTask(User user, TaskDifficulty difficulty) {
        while (true) {
            // Определяем параметры в зависимости от сложности
            int numOperands;
            int maxOperandValue;

            switch (difficulty) {
                case EASY:
                    numOperands = 2;
                    maxOperandValue = 10;
                    break;
                case MEDIUM:
                    numOperands = 3;
                    maxOperandValue = 50;
                    break;
                case HARD:
                    numOperands = 4;
                    maxOperandValue = 100;
                    break;
                case EXTREME:
                    numOperands = 5;
                    maxOperandValue = 200;
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный уровень сложности: " + difficulty);
            }

            System.out.println("→ Генерация задачи с numOperands=" + numOperands + ", maxOperandValue=" + maxOperandValue);

            List<Integer> operands = new ArrayList<>();
            List<String> operators = new ArrayList<>();

            // Генерируем операнды
            for (int j = 0; j < numOperands; j++) {
                operands.add(ThreadLocalRandom.current().nextInt(1, maxOperandValue + 1));
                if (j < numOperands - 1) {
                    operators.add(ThreadLocalRandom.current().nextBoolean() ? "+" : "-");
                }
            }

            // Формируем выражение и вычисляем результат
            StringBuilder expr = new StringBuilder();
            int result = operands.get(0);

            for (int j = 1; j < numOperands; j++) {
                expr.append(operands.get(j - 1)).append(' ')
                        .append(operators.get(j - 1)).append(' ');
                result = operators.get(j - 1).equals("+")
                        ? result + operands.get(j)
                        : result - operands.get(j);
            }
            expr.append(operands.get(numOperands - 1));

            // Проверяем, что результат неотрицательный
            if (result >= 0) {
                MathTask task = new MathTask();
                task.setExpression(expr.toString());
                task.setAnswer(result);
                task.setType(TaskType.MARATHON);
                task.setDifficulty(difficulty);
                task.setTimestamp(LocalDateTime.now());
                task.setUser(user);
                return task;
            }
            System.out.println("→ Отрицательный результат (" + result + "), повторная генерация");
        }
    }

    @Transactional
    public boolean submitAnswer(UUID taskId, double userAnswer) {
        try {
            MathTask task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));
            boolean correct = Math.abs(task.getAnswer() - userAnswer) < 0.0001;
            task.setUserAnswer(userAnswer);
            task.setCorrect(correct);
            taskRepository.save(task);
            System.out.println("Ответ сохранен: taskId=" + taskId + ", correct=" + correct + ", userAnswer=" + userAnswer);
            return correct;
        } catch (Exception e) {
            System.out.println("❌ Ошибка при сохранении ответа: taskId=" + taskId + ", error=" + e.getMessage());
            throw e;
        }
    }

    /**
     * Обновляет рекорд пользователя по итоговому score марафона.
     * Возвращает актуальный рекорд (после обновления).
     */
    @Transactional
    public int finishMarathon(User user, int score) {
        int current = user.getMarathonRecord();
        if (score > current) {
            user.setMarathonRecord(score);
            userRepository.save(user);
            System.out.println("🏆 Новый рекорд для пользователя " + user.getUsername() + ": " + score);
            return score;
        }
        System.out.println("ℹ️ Рекорд не обновлён. Текущий рекорд " + user.getUsername() + ": " + current + ", score: " + score);
        return current;
    }
}
