package com.calc.backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingSettingsDto {

    @NotBlank(message = "Сложность обязательна")
    private String difficulty;

    @Min(value = 1, message = "Минимум 1 пример")
    @Max(value = 100, message = "Максимум 100 примеров")
    private int numberOfExamples;

    @NotBlank(message = "Нужно указать хотя бы одну операцию")
    private String operations;

    @DecimalMin(value = "0.0", message = "Минимальное значение не может быть отрицательным")
    private double minOperand;

    @DecimalMax(value = "100000.0", message = "Слишком большое значение")
    private double maxOperand;



}
