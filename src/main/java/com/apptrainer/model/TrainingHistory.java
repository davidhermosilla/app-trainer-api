package com.apptrainer.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "training_history")
public class TrainingHistory {
	
	public enum PAYMENT_STATUS_TYPE {
		PAYED("Pagado"), PENDING("Pendiente"), CANCELED("Cancelado");

		private String value;

		// Constructor
		PAYMENT_STATUS_TYPE(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}	
	
	@Id
	@Column(name = "training_history_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(name = "pay_status", unique = false, nullable = false)
    private String payStatus;
	
	@Column(name = "training_date", unique = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date trainingDate;
	
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "training_id")
    private Training training;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Date getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

}
