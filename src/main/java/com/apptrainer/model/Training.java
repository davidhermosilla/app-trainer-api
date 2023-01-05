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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.apptrainer.service.util.AppTrainerUtil;
import com.apptrainer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Table(name = "training",
    uniqueConstraints=
        @UniqueConstraint(columnNames={"training_type"})
)
@Inheritance(strategy = InheritanceType.JOINED)
public class Training {
	@Autowired
	private static MessageSource mensajes;
	
	public enum TRAINING_DURATION_TYPE {
		DAILY(AppTrainerUtil.getString(mensajes,"Training.daily")), MONTHLY(AppTrainerUtil.getString(mensajes,"Training.monthly")), YEARLY(AppTrainerUtil.getString(mensajes,"Training.yearly")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		private String value;

		// Constructor
		TRAINING_DURATION_TYPE(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	@Id
	@Column(name = "training_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.Basic.class)
	private int id;
	
	@Column(name = "training_type", unique = false, nullable = false)
	@JsonView(View.Basic.class)
	protected String training_type; //1semanal, 2 semanal, 3 semanal
	
	@Column(name = "pryce", unique = false, nullable = true)
	@JsonView(View.Basic.class)
    private int pryce;
	
	@Column(name = "duration_type", unique = false, nullable = false)
	@JsonView(View.Basic.class)
    private String durationType;
	
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
    @JsonView(View.Extended.class)
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
	
	public void setPryce(int pryce) {
		this.pryce = pryce;
	}
	
	public int getPryce() {
        return pryce;
    }

	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public static MessageSource getMensajes() {
		return mensajes;
	}

	public static void setMensajes(MessageSource mensajes) {
		Training.mensajes = mensajes;
	}

}
