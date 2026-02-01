package com.calc.backend.generator.impl;

import com.calc.backend.entity.MathTask;
import com.calc.backend.enums.TaskDifficulty;
import com.calc.backend.enums.TaskType;
import com.calc.backend.entity.User;
import com.calc.backend.generator.TaskGenerator;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class RootTaskGenerator implements TaskGenerator {

    private static final Random RANDOM = new Random();

    @Override
    public String getOperation() {
        return "root";
    }

    @Override
    public List<MathTask> generate(int count,
                                   double min,
                                   double max,
                                   TaskDifficulty difficulty,
                                   TaskType type,
                                   User user) {

        List<MathTask> tasks = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            double root = getRandom(min, max);
            double square = root * root;
            root = Math.round(root * 100.0) / 100.0;
            square = Math.round(square * 100.0) / 100.0;

            String expression = String.format("√%.2f", square);

            tasks.add(MathTask.of(
                    expression,
                    root,
                    List.of(square),
                    List.of("root"),
                    difficulty,
                    type,
                    user
            ));
        }

        return tasks;
    }

    private double getRandom(double min, double max) {
        return Math.round((min + (max - min) * RANDOM.nextDouble()) * 100.0) / 100.0;
    }
}
