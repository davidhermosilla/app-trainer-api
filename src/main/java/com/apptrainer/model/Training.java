package com.apptrainer.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Table(name = "training",
    uniqueConstraints=
        @UniqueConstraint(columnNames={"training_type"})
)
@Inheritance(strategy = InheritanceType.JOINED)
public class Training {
	@Id
	@Column(name = "training_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "training_type", unique = false, nullable = false)
	protected String training_type; //1semanal, 2 semanal, 3 semanal
	
    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "athlete_training",
	    joinColumns = @JoinColumn(name = "training_id"),
	    inverseJoinColumns = @JoinColumn(name = "athlete_id"),
    	uniqueConstraints = {@UniqueConstraint(
    		name="athlete_training_pk",
            columnNames = {"athlete_id", "training_id"})}
    )
    private List<Athlete> athletes;	
	
    public Training() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Training(int id, String type, int pryce) {
        this.training_type = type;
    }


	public String getTraining_type() {
		return training_type;
	}

	public void setTraining_type(String training_type) {
		this.training_type = training_type;
	}

	public List<Athlete> getAthletes() {
		return athletes;
	}

	public void setAthletes(List<Athlete> athletes) {
		this.athletes = athletes;
	}

}
