package com.apptrainer.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apptrainer.constant.AppTrainerConstant;
import com.apptrainer.exception.AppTrainerException;
import com.apptrainer.model.Athlete;
import com.apptrainer.model.FitnessTraining;
import com.apptrainer.service.FitnessTrainingService;
import com.apptrainer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(AppTrainerConstant.APP_PREFIX+"/fitnesstrainings")
public class FitnessTrainingController {
	//TODO: Añadir control de excepciones
	static final Logger log = LoggerFactory.getLogger(FitnessTrainingController.class);
	
    @Autowired
    FitnessTrainingService fitnesstrainingService;

    @GetMapping("")
    @JsonView(View.Basic.class)
    public List<FitnessTraining> list() {
    	log.debug("List All Padel Trainings");
        return fitnesstrainingService.listAllFitnessTrainings();
    }

    @GetMapping("/{id}")
    @JsonView(View.Extended.class)
    public ResponseEntity<FitnessTraining> get(@PathVariable Integer id) {
        try {
        	FitnessTraining fitnessTraining = fitnesstrainingService.getFitnessTraining(id);
            return new ResponseEntity<FitnessTraining>(fitnessTraining, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<FitnessTraining>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/")
    @JsonView(View.Basic.class)
    public FitnessTraining add(@RequestBody FitnessTraining fitnessTraining) {
    	FitnessTraining fitnessReturn = fitnesstrainingService.saveFitnessTraining(fitnessTraining);
    	return fitnessReturn;
    }

    @PutMapping("/{id}")
    @JsonView(View.Basic.class)
    public ResponseEntity<?> update(@RequestBody FitnessTraining fitnessTraining, @PathVariable Integer id) {
        try {
        	fitnessTraining.setId(id);
        	fitnessTraining = fitnesstrainingService.saveFitnessTraining(fitnessTraining);
            return new ResponseEntity<FitnessTraining>(fitnessTraining,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/athletes")
    @JsonView(View.Extended.class)
    public ResponseEntity<?> update(@RequestBody List<Athlete> athletes, @PathVariable Integer id) {
        try {
        	FitnessTraining fitnessTrainingReturned = fitnesstrainingService.updateFitnessTrainingAthletes(id, athletes);
            return new ResponseEntity<FitnessTraining>(fitnessTrainingReturned,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AppTrainerException e) {
        	return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping("/{id}/athletes/{athlete_id}")
    @JsonView(View.Extended.class)
    public ResponseEntity<?> addAthlete(@PathVariable Integer id, @PathVariable Integer athlete_id) {
        try {
        	FitnessTraining fitnessTrainingReturned = fitnesstrainingService.addAthlete(id, athlete_id);
            return new ResponseEntity<FitnessTraining>(fitnessTrainingReturned,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AppTrainerException e) {
        	return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @DeleteMapping("/{id}/athletes/{athlete_id}")
    @JsonView(View.Extended.class)
    public ResponseEntity<?> deleteAthlete(@PathVariable Integer id, @PathVariable Integer athlete_id) {
        try {
        	FitnessTraining fitnessTrainingReturned = fitnesstrainingService.deleteAthlete(id, athlete_id);
            return new ResponseEntity<FitnessTraining>(fitnessTrainingReturned,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AppTrainerException e) {
        	return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }    
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

    	fitnesstrainingService.deleteFitnessTraining(id);
    }
    
    @PostMapping("/{id}/exercises/{exercise_id}")
    @JsonView(View.Extended.class)
    public ResponseEntity<?> addExercise(@PathVariable Integer id, @PathVariable Integer exercise_id) {
        try {
        	FitnessTraining fitnessTrainingReturned = fitnesstrainingService.addExercise(id, exercise_id);
            return new ResponseEntity<FitnessTraining>(fitnessTrainingReturned,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AppTrainerException e) {
        	return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @DeleteMapping("/{id}/athletes/{athlete_id}")
    @JsonView(View.Extended.class)
    public ResponseEntity<?> deleteExercise(@PathVariable Integer id, @PathVariable Integer exercise_id) {
        try {
        	FitnessTraining fitnessTrainingReturned = fitnesstrainingService.deleteExercise(id, exercise_id);
            return new ResponseEntity<FitnessTraining>(fitnessTrainingReturned,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AppTrainerException e) {
        	return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }   
}
