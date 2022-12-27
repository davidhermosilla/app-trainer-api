package com.apptrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apptrainer.model.Athlete;

public interface AthleteRepository extends JpaRepository<Athlete, Integer> {
}
