package com.calc.backend.repository;

import com.calc.backend.entity.TrainingSettings;
import com.calc.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainingSettingsRepository extends JpaRepository<TrainingSettings, Long> {
    Optional<TrainingSettings> findByUser(User user);
}
