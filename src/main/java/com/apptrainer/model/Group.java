package com.apptrainer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "athletes")
public class Athlete {
	@Id
	@Column(name = "athlete_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(name = "first_name", unique = false, nullable = false)
    private String firstName;
    
	@Column(name = "last_name", unique = false, nullable = false)
    private String lastName;
    
    @Column(name = "email", unique = false, nullable = true)
    private String email;
    
    @Column(name = "athlete_role", unique = false, nullable = true)
    private String athlete_role;
    

    public Athlete() {
    }

    public Athlete(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAthlete_role() {
		return athlete_role;
	}

	public void setAthlete_role(String athlete_role) {
		this.athlete_role = athlete_role;
	}

}
