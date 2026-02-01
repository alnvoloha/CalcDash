package com.calc.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "training_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String difficulty;

    @Column(nullable = false)
    private int numberOfExamples;

    @Column(nullable = false)
    private String operations;

    @Column(nullable = false)
    private double minOperand;

    @Column(nullable = false)
    private double maxOperand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
