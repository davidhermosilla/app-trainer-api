package com.apptrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apptrainer.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
}
