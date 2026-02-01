package com.calc.backend.repository;

import com.calc.backend.entity.MathTask;
import com.calc.backend.entity.User;
import com.calc.backend.enums.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MathTaskRepository extends JpaRepository<MathTask, UUID> {
    List<MathTask> findByUserAndCorrectFalse(User user);
    List<MathTask> findByUserAndType(User user, TaskType type);
    List<MathTask> findByUserAndTimestampAfter(User user, LocalDateTime after);

    int countByUser(User user);
    int countByUserAndCorrectTrue(User user);
    int countByUserAndCorrectTrueAndType(User user, TaskType type);
}