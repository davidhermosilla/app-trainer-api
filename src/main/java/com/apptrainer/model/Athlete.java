package com.apptrainer.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Table(name = "athletes",uniqueConstraints =  
	{@UniqueConstraint(
		name="athletes_unique_key",
        columnNames = {"first_name", "last_name"})
	})
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
    
    @ManyToMany(mappedBy = "athletes")
    @JsonIgnore
    private List<Training> trainings;
    
    @OneToMany(cascade = CascadeType.ALL )
    @JoinColumn(name = "athlete_id")
    private List<TrainingHistory> trainingHistories;   

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

    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object o) {
 
        // If the object is compared with itself then return true 
        if (o == this) {
            return true;
        }
 
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Athlete)) {
            return false;
        }
         
        // typecast o to Complex so that we can compare data members
        Athlete athlete = (Athlete) o;
         
        // Compare the data members and return accordingly
        return Athlete.compare(this,athlete);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (int) id;
        hash = 31 * hash + (getFirstName() == null ? 0 : getFirstName().hashCode());
        hash = 31 * hash + (getLastName() == null ? 0 : getLastName().hashCode());
        return hash;
    }    

	private static boolean compare(Athlete athlete, Athlete athlete2) {
		if ( (athlete!=null && athlete2!=null && 
				athlete.getFirstName().equals(athlete2.getFirstName())) 
				&& 
			 (athlete!=null && athlete2!=null &&
			    athlete!=null &&  athlete.getLastName().equals(athlete2.getLastName())) 
			) {
			return true;
		}
		return false;
	}

	public List<TrainingHistory> getTrainingHistories() {
		return trainingHistories;
	}

	public void setTrainingHistories(List<TrainingHistory> trainingHistories) {
		this.trainingHistories = trainingHistories;
	}

	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}

}
