package com.apptrainer.response;

import java.util.List;

import com.apptrainer.model.TrainingHistory;
import com.apptrainer.view.View;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public class PaysResponse {

	 @JsonProperty("total_pending")
	 @JsonView(View.Basic.class)
	 private float total_pending = 0;
	 
	 @JsonProperty("pending_list")
	 @JsonView(View.Basic.class)
	 private List<TrainingHistory> pendingTrainingHistory;

	public List<TrainingHistory> getPendingTrainingHistory() {
		return pendingTrainingHistory;
	}

	public void setPendingTrainingHistory(List<TrainingHistory> pendingTrainingHistory) {
		this.pendingTrainingHistory = pendingTrainingHistory;
	}

	public float getTotal_pending() {
		return total_pending;
	}

	public void setTotal_pending(float total_pending) {
		this.total_pending = total_pending;
	}

}
