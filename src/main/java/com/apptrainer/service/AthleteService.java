package com.apptrainer.service;

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

    public void saveAthlete(Athlete user) {
        athleteRepository.save(user);
    }

    public Athlete getAthlete(Integer id) {
        return athleteRepository.findById(id).get();
    }

    public void deleteAthlete(Integer id) {
        athleteRepository.deleteById(id);
    }
    
	public List<TrainingHistory> addTrainingHistory(Integer training_id, Integer athlete_id,TrainingHistory trainingHistory) throws AppTrainerException {
		
		Training training = AppTrainerUtil.checkTraining(trainingRepository, training_id);
		
		Athlete athlete = AppTrainerUtil.checkAthlete(athleteRepository, athlete_id);
		
		checkAthleteAssigned(training_id, athlete_id, athlete);
		
		if (trainingHistory.getTrainingDate()==null) {
			throw new AppTrainerException("Error no ha asignado la fecha del entrenamiento");
		}
		
		trainingHistory.setPayStatus(TrainingHistory.PAYMENT_STATUS_TYPE.PENDING.getValue());
		trainingHistory.setTraining(training);
		
		athlete.getTrainingHistories().add(trainingHistory);
		
		athlete.getTrainings().add(training);
		
		Athlete atheleteReturned = athleteRepository.save(athlete);
		
		return atheleteReturned.getTrainingHistories();
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

}
