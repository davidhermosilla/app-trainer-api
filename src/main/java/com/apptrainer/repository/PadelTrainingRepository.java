package com.apptrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apptrainer.model.PadelTraining;

public interface PadelTrainingRepository extends JpaRepository<PadelTraining, Integer> {
}
