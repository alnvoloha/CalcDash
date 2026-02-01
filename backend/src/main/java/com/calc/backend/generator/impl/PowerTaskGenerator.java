package com.calc.backend.generator.impl;

import com.calc.backend.entity.MathTask;
import com.calc.backend.enums.TaskDifficulty;
import com.calc.backend.enums.TaskType;
import com.calc.backend.entity.User;
import com.calc.backend.generator.TaskGenerator;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PowerTaskGenerator implements TaskGenerator {

    private static final Random RANDOM = new Random();

    @Override
    public String getOperation() {
        return "pow";
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
            int base = (int) getRandom(min, max);
            int exponent = RANDOM.nextInt(3) + 2; // степень 2–4
            double result = Math.pow(base, exponent);

            String expression = base + "^" + exponent;

            tasks.add(MathTask.of(
                    expression,
                    result,
                    List.of((double) base, (double) exponent),
                    List.of("pow"),
                    difficulty,
                    type,
                    user
            ));
        }

        return tasks;
    }

    private double getRandom(double min, double max) {
        return RANDOM.nextInt((int) max - (int) min + 1) + (int) min;
    }
}
