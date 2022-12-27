package com.apptrainer.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "padeltraining",
    uniqueConstraints=
        @UniqueConstraint(columnNames={"training_type"})
)
public class PadelTraining {
	@Id
	@Column(name = "padeltraining_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "training_type", unique = false, nullable = false)
	private String training_type; //1semanal, 2 semanal, 3 semanal
	
	@Column(name = "pryce", unique = false, nullable = true)
    private int pryce;
	
	@Column(name = "level", unique = false, nullable = true)
    private String level;
	
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "padeltraining_id")
    private Set<Athlete> athletes;

    public PadelTraining() {
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public PadelTraining(int id, String type, int pryce) {
        this.training_type = type;
        this.pryce = pryce;
    }

	public void setPryce(int pryce) {
		this.pryce = pryce;
	}
	
	public int getPryce() {
        return pryce;
    }

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getTraining_type() {
		return training_type;
	}

	public void setTraining_type(String training_type) {
		this.training_type = training_type;
	}


	public Set<Athlete> getAthletes() {
		return athletes;
	}


	public void setAthletes(Set<Athlete> athletes) {
		this.athletes = athletes;
	}

}
