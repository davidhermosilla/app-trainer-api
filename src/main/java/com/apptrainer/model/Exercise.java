package com.apptrainer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.apptrainer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "exercise")
public class Exercise {

    public Exercise() {
    }
    
	@Id
	@Column(name = "exercise_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({View.Basic.class, View.Fitness.class})
    private int id;
    
	@Column(name = "exercise_name", unique = false, nullable = false)
	@JsonView(View.Basic.class)
    private String exerciseName;
    
	@Column(name = "exercise_description", unique = false, nullable = true)
	@JsonView(View.Basic.class)
    private String exerciseDescription;
	
	@Column(name = "reps", unique = false, nullable = false)
	@JsonView(View.Basic.class)
    private int reps;
	
	@Column(name = "rest_interval", unique = false, nullable = false)
	@JsonView(View.Basic.class)
    private int resInterval=0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public String getExerciseDescription() {
		return exerciseDescription;
	}

	public void setExerciseDescription(String exerciseDescription) {
		this.exerciseDescription = exerciseDescription;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public int getResInterval() {
		return resInterval;
	}

	public void setResInterval(int resInterval) {
		this.resInterval = resInterval;
	}
	
	
}
