package com.apptrainer.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.apptrainer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "fitnesstraining")
@PrimaryKeyJoinColumn(name = "fitnesstraining_id")
public class FitnessTraining extends Training {

    public FitnessTraining() {
    }
    
	@Column(name = "duration_session", unique = false, nullable = false)
	@JsonView(View.Basic.class)
    private String duration_session;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "fitnesstraining_id")
    @JsonView({View.Group.class})
    private List<Exercise> exercises;
	
	public String getDuration_session() {
		return duration_session;
	}

	public void setDuration_session(String duration_session) {
		this.duration_session = duration_session;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}
    

}
