package com.apptrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apptrainer.model.Training;

public interface TrainingRepository extends JpaRepository<Training, Integer> {
}
