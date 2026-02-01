package com.calc.backend.generator.impl;

import com.calc.backend.entity.MathTask;
import com.calc.backend.enums.TaskDifficulty;
import com.calc.backend.enums.TaskType;
import com.calc.backend.entity.User;
import com.calc.backend.generator.TaskGenerator;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MultiplicationTaskGenerator implements TaskGenerator {

    private static final Random RANDOM = new Random();

    @Override
    public String getOperation() {
        return "*";
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
            double a = getRandom(min, max);
            double b = getRandom(min, max);
            double result = a * b;

            String expression = String.format("%.2f * %.2f", a, b);

            tasks.add(MathTask.of(
                    expression,
                    result,
                    List.of(a, b),
                    List.of("*"),
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
