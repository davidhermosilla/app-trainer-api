package com.apptrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apptrainer.model.FitnessTraining;

public interface FitnessTrainingRepository extends JpaRepository<FitnessTraining, Integer> {
}
