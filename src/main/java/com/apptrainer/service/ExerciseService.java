package com.apptrainer.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apptrainer.model.Exercise;
import com.apptrainer.repository.ExerciseRepository;

@Service
@Transactional
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;
    
    @Autowired
	private MessageSource mensajes;
    
    public List<Exercise> listAllExcercises() {
        return exerciseRepository.findAll();
    }

    public Exercise saveExercise(Exercise training) {
    	Optional<Exercise> trainingSaved = exerciseRepository.findById(training.getId());
    	if (trainingSaved.isPresent()) {
    		Exercise exerciseReturned = trainingSaved.get();
    		
    		if (!StringUtils.isEmpty(training.getExerciseName())) 
    			exerciseReturned.setExerciseName(training.getExerciseName());
    		
    		if (!StringUtils.isEmpty(training.getExerciseDescription()))
    			exerciseReturned.setExerciseDescription(training.getExerciseDescription());
    		
    		if (training.getReps()!=0)    			
    			exerciseReturned.setReps(training.getReps()); 

    		if (training.getResInterval()!=0)    			
    			exerciseReturned.setResInterval(training.getResInterval()); 
    		
    		if (!StringUtils.isEmpty(training.getVideoUrl()))
    			exerciseReturned.setVideoUrl(training.getVideoUrl());

    		
    		return exerciseRepository.save(exerciseReturned);	
    	} else {
    		return exerciseRepository.save(training);
    	}
    }

	public ExerciseRepository getExerciseRepository() {
		return exerciseRepository;
	}

	public void setExerciseRepository(ExerciseRepository exerciseRepository) {
		this.exerciseRepository = exerciseRepository;
	}

    public Exercise getExercise(int id) {
        return exerciseRepository.findById(id).get();
    }

    public void deleteExercise(Integer id) {
        exerciseRepository.deleteById(id);
    }

	public MessageSource getMensajes() {
		return mensajes;
	}

	public void setMensajes(MessageSource mensajes) {
		this.mensajes = mensajes;
	}	

}
