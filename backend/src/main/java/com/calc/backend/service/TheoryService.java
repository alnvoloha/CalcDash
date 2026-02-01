package com.calc.backend.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TheoryService {

    private final Map<String, String> theory = Map.of(
            "trachtenberg", "Trachtenberg system: быстрые приемы устного счета. (позже вставишь нормальный текст)",
            "chinese", "Chinese multiplication: таблицы и разложение на части. (позже вставишь нормальный текст)",
            "factorial", "Factorials: что такое n! и как прикидывать значения. (позже вставишь нормальный текст)",
            "vedic", "Vedic math: приемы быстрого умножения. (позже вставишь нормальный текст)"
    );

    public String getTheory(String topic) {
        return theory.getOrDefault(topic.toLowerCase(), "No theory available for this topic yet.");
    }
}
