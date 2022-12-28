package com.apptrainer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apptrainer.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
