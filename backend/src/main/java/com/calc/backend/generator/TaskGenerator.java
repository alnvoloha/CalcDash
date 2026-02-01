package com.calc.backend.generator;

import com.calc.backend.entity.MathTask;
import com.calc.backend.enums.TaskDifficulty;
import com.calc.backend.enums.TaskType;
import com.calc.backend.entity.User;

import java.util.List;

public interface TaskGenerator {
    String getOperation(); // например, "+"
    List<MathTask> generate(int count,
                            double min,
                            double max,
                            TaskDifficulty difficulty,
                            TaskType type,
                            User user);
}
