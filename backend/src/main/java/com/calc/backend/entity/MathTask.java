package com.calc.backend.entity;

import com.calc.backend.enums.TaskDifficulty;
import com.calc.backend.enums.TaskType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "math_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MathTask {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String expression;

    @Column(nullable = false)
    private double answer;

    @ElementCollection
    @CollectionTable(name = "task_operands", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "operand")
    private List<Double> operands;

    @ElementCollection
    @CollectionTable(name = "task_operators", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "operator")
    private List<String> operators;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType type;

    @CreationTimestamp
    private LocalDateTime timestamp;

    private Double userAnswer;

    private Boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static MathTask of(String expression,
                              double answer,
                              List<Double> operands,
                              List<String> operators,
                              TaskDifficulty difficulty,
                              TaskType type,
                              User user) {
        MathTask task = new MathTask();
        task.setExpression(expression);
        task.setAnswer(answer);
        task.setOperands(operands);
        task.setOperators(operators);
        task.setDifficulty(difficulty);
        task.setType(type);
        task.setUser(user);
        return task;
    }



}
