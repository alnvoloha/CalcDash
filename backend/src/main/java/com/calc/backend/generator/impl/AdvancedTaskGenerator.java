package com.calc.backend.generator.impl;

import com.calc.backend.entity.MathTask;
import com.calc.backend.enums.TaskDifficulty;
import com.calc.backend.enums.TaskType;
import com.calc.backend.entity.User;
import com.calc.backend.generator.TaskGenerator;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AdvancedTaskGenerator implements TaskGenerator {

    private static final Random RANDOM = new Random();

    @Override
    public String getOperation() {
        return "ADVANCED";
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
            int operandsCount = 3 + RANDOM.nextInt(3); // от 3 до 5
            List<Double> operands = new ArrayList<>();
            List<String> operators = new ArrayList<>();
            StringBuilder expression = new StringBuilder();

            for (int j = 0; j < operandsCount; j++) {
                double operand = getRandom(min, max);
                operands.add(operand);
                expression.append(String.format("%.2f", operand));
                if (j < operandsCount - 1) {
                    String op = pickRandomOperator();
                    operators.add(op);
                    expression.append(" ").append(op).append(" ");
                }
            }

            double answer = evaluate(operands, operators);
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
        return min + (max - min) * RANDOM.nextDouble();
    }

    private String pickRandomOperator() {
        String[] ops = {"+", "-", "*"};
        return ops[RANDOM.nextInt(ops.length)];
    }

    private double evaluate(List<Double> operands, List<String> operators) {
        double result = operands.get(0);
        for (int i = 1; i < operands.size(); i++) {
            String op = operators.get(i - 1);
            double next = operands.get(i);
            result = switch (op) {
                case "+" -> result + next;
                case "-" -> result - next;
                case "*" -> result * next;
                case "/" -> next != 0 ? result / next : result;
                default -> result;
            };
        }
        return result;
    }
}
