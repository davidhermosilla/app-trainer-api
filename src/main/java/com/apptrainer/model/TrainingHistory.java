package com.apptrainer.model;

import java.util.Date;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.apptrainer.service.ApplicationContextProvider;
import com.apptrainer.service.util.AppTrainerUtil;
import com.apptrainer.view.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "training_history")
public class TrainingHistory {
	@Autowired
	private static MessageSource mensajes;
	
	public enum PAYMENT_STATUS_TYPE {
		PAYED(AppTrainerUtil.getString(mensajes,"TrainingHistory.payed")), PENDING(AppTrainerUtil.getString(mensajes,"TrainingHistory.pending")), CANCELED(AppTrainerUtil.getString(mensajes,"TrainingHistory.canceled")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

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
	@JsonView(View.Basic.class)
    private int id;
	
	@Column(name = "pay_status", unique = false, nullable = false)
	@JsonView(View.Basic.class)
    private String payStatus;
	
	@Column(name = "training_date", unique = false, nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonView(View.Basic.class)
    private Date trainingDate;
	
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "training_id")
    @JsonView(View.Basic.class)
    private Training training;
    
    @JsonProperty("repeat_training")
    @JsonView(View.None.class)
    private int repeat_training = 0;
	
    public TrainingHistory() {
    	mensajes=ApplicationContextProvider.bean(MessageSource.class);
    }
    
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

	public int getRepeat_training() {
		return repeat_training;
	}

	public void setRepeat_training(int repeat_training) {
		this.repeat_training = repeat_training;
	}

	public static MessageSource getMensajes() {
		return mensajes;
	}

	public static void setMensajes(MessageSource mensajes) {
		TrainingHistory.mensajes = mensajes;
	}


}
