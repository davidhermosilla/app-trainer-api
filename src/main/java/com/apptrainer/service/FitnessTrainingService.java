package com.apptrainer.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apptrainer.exception.AppTrainerException;
import com.apptrainer.model.Athlete;
import com.apptrainer.model.FitnessTraining;
import com.apptrainer.repository.AthleteRepository;
import com.apptrainer.repository.FitnessTrainingRepository;
import com.apptrainer.service.util.AppTrainerUtil;

@Service
@Transactional
public class FitnessTrainingService {
    @Autowired
    private FitnessTrainingRepository fitnessTrainingRepository;
    
    @Autowired
    private AthleteRepository athleteRepository;
    
    public List<FitnessTraining> listAllFitnessTrainings() {
        return fitnessTrainingRepository.findAll();
    }

    public FitnessTraining saveFitnessTraining(FitnessTraining training) {
    	Optional<FitnessTraining> trainingSaved = fitnessTrainingRepository.findById(training.getId());
    	if (trainingSaved.isPresent()) {
    		FitnessTraining fitnessTrainingReturned = trainingSaved.get();
    		
    		if (!StringUtils.isEmpty(training.getDuration_session())) 
    			fitnessTrainingReturned.setDuration_session(training.getDuration_session());
    		
    		if (!StringUtils.isEmpty(training.getPryce()))
    			fitnessTrainingReturned.setPryce(training.getPryce());
    		
    		if (!StringUtils.isEmpty(training.getTraining_type()))    			
    			fitnessTrainingReturned.setTraining_type(training.getTraining_type()); 
    		
    		return fitnessTrainingRepository.save(fitnessTrainingReturned);	
    	} else {
    		return fitnessTrainingRepository.save(training);
    	}
    }

    public FitnessTraining updateFitnessTrainingAthletes(int fitnessTrainingId, List<Athlete> athletes) throws AppTrainerException {
    	FitnessTraining fitnessTraining = fitnessTrainingRepository.findById(fitnessTrainingId).get();
    	
    	AppTrainerUtil.addAthletes(athleteRepository,athletes, fitnessTraining);
    	
		AppTrainerUtil.removeAthletes(athletes, fitnessTraining);
    	
    	return fitnessTrainingRepository.saveAndFlush(fitnessTraining);
    }
    
    public FitnessTraining addAthlete(int fitnessTrainingId, int athlete_id) throws AppTrainerException {
    	FitnessTraining fitnessTraining = fitnessTrainingRepository.findById(fitnessTrainingId).get();
    	
    	Athlete athlete = AppTrainerUtil.checkAthlete(athleteRepository,athlete_id);
    	
    	fitnessTraining.getAthletes().add(athlete);
    	
    	return fitnessTrainingRepository.saveAndFlush(fitnessTraining);
    }
    
	public FitnessTraining deleteAthlete(Integer fitnessTrainingId, Integer athlete_id) throws AppTrainerException {
    	FitnessTraining fitnessTraining = fitnessTrainingRepository.findById(fitnessTrainingId).get();
    	
    	Athlete athlete = AppTrainerUtil.checkAthlete(athleteRepository,athlete_id);
    	
    	fitnessTraining.getAthletes().remove(athlete);
    	
    	return fitnessTrainingRepository.saveAndFlush(fitnessTraining);
	}    

	public FitnessTrainingRepository getFitnessTrainingRepository() {
		return fitnessTrainingRepository;
	}

	public void setFitnessTrainingRepository(FitnessTrainingRepository fitnessTrainingRepository) {
		this.fitnessTrainingRepository = fitnessTrainingRepository;
	}

	public AthleteRepository getAthleteRepository() {
		return athleteRepository;
	}

	public void setAthleteRepository(AthleteRepository athleteRepository) {
		this.athleteRepository = athleteRepository;
	}
	
    public FitnessTraining getFitnessTraining(int id) {
        return fitnessTrainingRepository.findById(id).get();
    }

    public void deleteFitnessTraining(Integer id) {
        fitnessTrainingRepository.deleteById(id);
    }	

}
