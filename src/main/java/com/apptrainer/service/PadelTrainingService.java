package com.apptrainer.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apptrainer.model.Athlete;
import com.apptrainer.model.PadelTraining;
import com.apptrainer.repository.PadelTrainingRepository;
@Service
@Transactional
public class PadelTrainingService {
    @Autowired
    private PadelTrainingRepository padelTrainingRepository;
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
    		return null;
    	}
    }

    public PadelTraining getPadelTraining(int id) {
        return padelTrainingRepository.findById(id).get();
    }

    public void deletePadelTraining(Integer id) {
        padelTrainingRepository.deleteById(id);
    }
    
    public PadelTraining updatePadelTrainingAthletes(int padelTrainingId, List<Athlete> athletes) {
    	PadelTraining padelTraining = padelTrainingRepository.findById(padelTrainingId).get();
    	padelTraining.getAthletes().addAll(athletes);
    	return padelTrainingRepository.saveAndFlush(padelTraining);
    }
}
