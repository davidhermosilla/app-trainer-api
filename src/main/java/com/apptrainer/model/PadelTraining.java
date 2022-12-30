package com.apptrainer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.apptrainer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "padeltraining")
@PrimaryKeyJoinColumn(name = "padeltraining_id")
public class PadelTraining extends Training {

    public PadelTraining() {
    }
    
	@Column(name = "level", unique = false, nullable = true)
	@JsonView(View.Basic.class)
    private String level;
    
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
