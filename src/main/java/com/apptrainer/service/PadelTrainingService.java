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
    	
    	addAthletes(athletes, padelTraining);
    	
		removeAthletes(athletes, padelTraining);
    	
    	return padelTrainingRepository.saveAndFlush(padelTraining);
    }
    
    public PadelTraining addAthlete(int padelTrainingId, int athlete_id) throws AppTrainerException {
    	PadelTraining padelTraining = padelTrainingRepository.findById(padelTrainingId).get();
    	
    	Athlete athlete = checkAthlete(athlete_id);
    	
    	padelTraining.getAthletes().add(athlete);
    	
    	return padelTrainingRepository.saveAndFlush(padelTraining);
    }
    
	public PadelTraining deleteAthlete(Integer padelTrainingId, Integer athlete_id) throws AppTrainerException {
    	PadelTraining padelTraining = padelTrainingRepository.findById(padelTrainingId).get();
    	
    	Athlete athlete = checkAthlete(athlete_id);
    	
    	padelTraining.getAthletes().remove(athlete);
    	
    	return padelTrainingRepository.saveAndFlush(padelTraining);
	}    

	/**
	 * @param athletes
	 * @param padelTraining
	 */
	private void removeAthletes(List<Athlete> athletes, PadelTraining padelTraining) {
		List<Athlete> athletesdb = new ArrayList<Athlete>(padelTraining.getAthletes());
		for (Athlete athlete: athletesdb) {
    		
    		boolean encontrado = false;
    		for (Athlete athleterequest: athletes) {
    			if (athleterequest.equals(athlete)) {
    				encontrado=true;
    				break;
    			}
    		}
    		
    		if (!encontrado) {
    			padelTraining.getAthletes().remove(athlete);
    		}
    		
    	}
	}

	/**
	 * @param athletes
	 * @param padelTraining
	 * @throws AppTrainerException
	 */
	private void addAthletes(List<Athlete> athletes, PadelTraining padelTraining) throws AppTrainerException {
		for (Athlete athlete: athletes) {
    		checkAthlete(athlete.getId());
    		
    		boolean encontrado = false;
    		for (Athlete athletedb: padelTraining.getAthletes()) {
    			if (athletedb.equals(athlete)) {
    				encontrado=true;
    				break;
    			}
    		}
    		
    		if (!encontrado) {
    			padelTraining.getAthletes().add(athlete);
    		}
    		
    	}
	}

	/**
	 * @param athlete
	 * @return 
	 * @throws AppTrainerException
	 */
	private Athlete checkAthlete(int athlete_id) throws AppTrainerException {
		Optional<Athlete> athletedbexist = athleteRepository.findById(athlete_id);
		
		//If the athleta does not exists error because should be created previously
		if (!athletedbexist.isPresent()) {
			throw new AppTrainerException("Error: El atleta con id: "+ athlete_id+" no existe");
		}
		
		return athletedbexist.get();
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
