package com.apptrainer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apptrainer.exception.AppTrainerException;
import com.apptrainer.model.Athlete;
import com.apptrainer.model.PadelTraining;
import com.apptrainer.repository.AthleteRepository;
import com.apptrainer.repository.PadelTrainingRepository;
import com.apptrainer.service.util.AppTrainerUtil;
@Service
@Transactional
public class PadelTrainingService {
    @Autowired
    private PadelTrainingRepository padelTrainingRepository;
    
    @Autowired
    private AthleteRepository athleteRepository;
    
    public List<PadelTraining> listAllPadelTrainings() {
        return padelTrainingRepository.findAll();
    }

    public PadelTraining savePadelTraining(PadelTraining training) {
    	Optional<PadelTraining> trainingSaved = padelTrainingRepository.findById(training.getId());
    	if (trainingSaved.isPresent()) {
    		PadelTraining padelTrainingReturned = trainingSaved.get();
    		
    		if (!StringUtils.isEmpty(training.getLevel())) 
    			padelTrainingReturned.setLevel(training.getLevel());
    		
    		if (!StringUtils.isEmpty(training.getPryce()))
    			padelTrainingReturned.setPryce(training.getPryce());
    		
    		if (!StringUtils.isEmpty(training.getTraining_type()))    			
    			padelTrainingReturned.setTraining_type(training.getTraining_type()); 
    		
    		return padelTrainingRepository.save(padelTrainingReturned);	
    	} else {
    		return padelTrainingRepository.save(training);
    	}
    }

    public PadelTraining updatePadelTrainingAthletes(int padelTrainingId, List<Athlete> athletes) throws AppTrainerException {
    	PadelTraining padelTraining = padelTrainingRepository.findById(padelTrainingId).get();
    	
    	AppTrainerUtil.addAthletes(athleteRepository,athletes, padelTraining);
    	
		AppTrainerUtil.removeAthletes(athletes, padelTraining);
    	
    	return padelTrainingRepository.saveAndFlush(padelTraining);
    }
    
    public PadelTraining addAthlete(int padelTrainingId, int athlete_id) throws AppTrainerException {
    	PadelTraining padelTraining = padelTrainingRepository.findById(padelTrainingId).get();
    	
    	Athlete athlete = AppTrainerUtil.checkAthlete(athleteRepository,athlete_id);
    	
    	padelTraining.getAthletes().add(athlete);
    	
    	return padelTrainingRepository.saveAndFlush(padelTraining);
    }
    
	public PadelTraining deleteAthlete(Integer padelTrainingId, Integer athlete_id) throws AppTrainerException {
    	PadelTraining padelTraining = padelTrainingRepository.findById(padelTrainingId).get();
    	
    	Athlete athlete = AppTrainerUtil.checkAthlete(athleteRepository,athlete_id);
    	
    	padelTraining.getAthletes().remove(athlete);
    	
    	return padelTrainingRepository.saveAndFlush(padelTraining);
	}    

	public PadelTrainingRepository getPadelTrainingRepository() {
		return padelTrainingRepository;
	}

	public void setPadelTrainingRepository(PadelTrainingRepository padelTrainingRepository) {
		this.padelTrainingRepository = padelTrainingRepository;
	}

	public AthleteRepository getAthleteRepository() {
		return athleteRepository;
	}

	public void setAthleteRepository(AthleteRepository athleteRepository) {
		this.athleteRepository = athleteRepository;
	}
	
    public PadelTraining getPadelTraining(int id) {
        return padelTrainingRepository.findById(id).get();
    }

    public void deletePadelTraining(Integer id) {
        padelTrainingRepository.deleteById(id);
    }	

}
