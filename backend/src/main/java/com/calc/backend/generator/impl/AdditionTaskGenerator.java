package com.calc.backend.generator.impl;

import com.calc.backend.entity.MathTask;
import com.calc.backend.enums.TaskDifficulty;
import com.calc.backend.enums.TaskType;
import com.calc.backend.entity.User;
import com.calc.backend.generator.TaskGenerator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class AdditionTaskGenerator implements TaskGenerator {

    private static final Random RANDOM = new Random();

    @Override
    public String getOperation() {
        return "+";
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
            int operandCount = 2; // можно сделать параметром позже

            List<Double> operands = new ArrayList<>();
            List<String> operators = new ArrayList<>();
            StringBuilder expression = new StringBuilder();

            for (int j = 0; j < operandCount; j++) {
                double num = getRandom(min, max);
                operands.add(num);
                expression.append(String.format("%.2f", num));

                if (j < operandCount - 1) {
                    operators.add("+");
                    expression.append(" + ");
                }
            }

            double answer = operands.stream().mapToDouble(Double::doubleValue).sum();

            tasks.add(MathTask.of(
                    expression.toString(),
                    answer,
                    operands,
                    operators,
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
