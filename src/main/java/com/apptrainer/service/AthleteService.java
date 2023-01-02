package com.apptrainer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apptrainer.exception.AppTrainerException;
import com.apptrainer.model.Athlete;
import com.apptrainer.model.Training;
import com.apptrainer.model.TrainingHistory;
import com.apptrainer.repository.AthleteRepository;
import com.apptrainer.repository.TrainingRepository;
import com.apptrainer.response.PaysResponse;
import com.apptrainer.service.util.AppTrainerUtil;
@Service
@Transactional
public class AthleteService {
    @Autowired
    private AthleteRepository athleteRepository;
    
    @Autowired
    private TrainingRepository trainingRepository;
    
    public List<Athlete> listAll() {
        return athleteRepository.findAll();
    }

    public Athlete saveAthlete(Athlete user) {
        return athleteRepository.save(user);
    }

    public Athlete getAthlete(Integer id) {
        return athleteRepository.findById(id).get();
    }

    public void deleteAthlete(Integer id) {
        athleteRepository.deleteById(id);
    }
    
	public Athlete addTrainingHistory(Integer training_id, Integer athlete_id,TrainingHistory trainingHistory) throws AppTrainerException {
		
		Training training = AppTrainerUtil.checkTraining(trainingRepository, training_id);
		
		Athlete athlete = AppTrainerUtil.checkAthlete(athleteRepository, athlete_id);
		
		checkAthleteAssigned(training_id, athlete_id, athlete);
		
		if (trainingHistory.getTrainingDate()==null) {
			throw new AppTrainerException("Error no ha asignado la fecha del entrenamiento");
		}
		
		checkTrainingMonthly(athlete_id, trainingHistory, training, athlete);
		
		trainingHistory.setPayStatus(TrainingHistory.PAYMENT_STATUS_TYPE.PENDING.getValue());
		trainingHistory.setTraining(training);
		
		athlete.getTrainingHistories().add(trainingHistory);
		
		athlete.getTrainings().add(training);
		
		Athlete atheleteReturned = athleteRepository.save(athlete);
		
		return atheleteReturned;
	}

	/**
	 * @param athlete_id
	 * @param trainingHistory
	 * @param training
	 * @param athlete
	 * @throws AppTrainerException
	 */
	public void checkTrainingMonthly(Integer athlete_id, TrainingHistory trainingHistory, Training training,
			Athlete athlete) throws AppTrainerException {
		if (Training.TRAINING_DURATION_TYPE.MONTHLY.getValue().equals(training.getDurationType())) {
			List<TrainingHistory> trainingHistories = athlete.getTrainingHistories();
			for (TrainingHistory th:trainingHistories) {
				if (Training.TRAINING_DURATION_TYPE.MONTHLY.getValue().equals(th.getTraining().getDurationType())) {
					int thyear = th.getTrainingDate().getYear();
					int thmonth = th.getTrainingDate().getMonth();
					int newyear = trainingHistory.getTrainingDate().getYear();
					int newmont =  trainingHistory.getTrainingDate().getMonth();
					if (thyear==newyear && thmonth==newmont) {
						throw new AppTrainerException("El athleta con id "+athlete_id+" ya tiene asignado un entrenamiento mensual en esa fecha.("+training.getTraining_type()+") desasigne este si desea asignar uno diferente en dicha fecha.");
					}
				}
			}
		}
	}

	/**
	 * @param training_id
	 * @param athlete_id
	 * @param athlete
	 * @throws AppTrainerException
	 */
	public void checkAthleteAssigned(Integer training_id, Integer athlete_id, Athlete athlete)
			throws AppTrainerException {
		List<Training> trainings = athlete.getTrainings();
		boolean encontrado = false;
		for (Training trainingSet:trainings) {
			if (trainingSet.getId()==training_id) {
				encontrado=true;
				break;
			}
		}
		
		if (!encontrado) {
			throw new AppTrainerException("El athleta "+athlete_id+" no tiene asignado el entrenamiento "+training_id+" asignelo y intentelo de nuevo");
		}
	}

	public Athlete addTrainingsHistory(Integer training_id, Integer athlete_id, TrainingHistory training,int repeat_training) throws AppTrainerException {
		Athlete athlete=null;
		for (int i=0;i<repeat_training;i++) {			
			athlete = addTrainingHistory(training_id, athlete_id, training);
			Date training_date = training.getTrainingDate();
			
			Date newdate = AppTrainerUtil.addMonth(training_date);
			
			training.setTrainingDate(newdate);
		}
		return athlete;
	}

	public PaysResponse getPendingPays(Integer athlete_id) {
		Athlete athlete = athleteRepository.findById(athlete_id).get();
		
		List<TrainingHistory> listTrainings = athlete.getTrainingHistories();
		List<TrainingHistory> pending = new ArrayList<TrainingHistory>();
		float total=0;
		PaysResponse pr = new PaysResponse();
		for (TrainingHistory th:listTrainings) {
			pending.add(th);
			total+=th.getTraining().getPryce();
		}

		pr.setPendingTrainingHistory(pending);
		pr.setTotal_pending(total);
		return pr;
	}

}
