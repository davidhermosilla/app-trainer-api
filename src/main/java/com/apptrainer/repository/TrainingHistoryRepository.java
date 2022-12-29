package com.apptrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apptrainer.model.TrainingHistory;

public interface TrainingHistoryRepository extends JpaRepository<TrainingHistory, Integer> {
}
