package com.calc.backend.generator;

import com.calc.backend.entity.MathTask;
import com.calc.backend.enums.TaskDifficulty;
import com.calc.backend.enums.TaskType;
import com.calc.backend.entity.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TaskGenerationManager {

    private final Map<String, TaskGenerator> generatorMap = new HashMap<>();

    public TaskGenerationManager(List<TaskGenerator> generators) {
        for (TaskGenerator generator : generators) {
            generatorMap.put(generator.getOperation(), generator);
        }
    }

    public List<MathTask> generateTasks(
            List<String> operations,
            int count,
            double minOperand,
            double maxOperand,
            TaskDifficulty difficulty,
            TaskType type,
            User user
    ) {
        List<MathTask> result = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String op = operations.get(random.nextInt(operations.size()));
            TaskGenerator generator = generatorMap.get(op);

            if (generator != null) {
                result.addAll(generator.generate(1, minOperand, maxOperand, difficulty, type, user));
            }
        }

        return result;
    }
}
