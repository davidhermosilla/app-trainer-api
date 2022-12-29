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
import com.apptrainer.model.PadelTraining;
import com.apptrainer.service.PadelTrainingService;

@RestController
@RequestMapping(AppTrainerConstant.APP_PREFIX+"/padeltrainings")
public class PadelTrainingController {
	
	static final Logger log = LoggerFactory.getLogger(PadelTrainingController.class);
	
    @Autowired
    PadelTrainingService padelTrainingService;

    @GetMapping("")
    public List<PadelTraining> list() {
    	log.debug("List All Padel Trainings");
        return padelTrainingService.listAllPadelTrainings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PadelTraining> get(@PathVariable Integer id) {
        try {
        	PadelTraining padelTraining = padelTrainingService.getPadelTraining(id);
            return new ResponseEntity<PadelTraining>(padelTraining, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<PadelTraining>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/")
    public PadelTraining add(@RequestBody PadelTraining padelTraining) {
    	PadelTraining padelReturn = padelTrainingService.savePadelTraining(padelTraining);
    	return padelReturn;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody PadelTraining padelTraining, @PathVariable Integer id) {
        try {
        	padelTraining.setId(id);
        	padelTraining = padelTrainingService.savePadelTraining(padelTraining);
            return new ResponseEntity<PadelTraining>(padelTraining,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/athletes")
    public ResponseEntity<?> update(@RequestBody List<Athlete> athletes, @PathVariable Integer id) {
        try {
        	PadelTraining padelTrainingReturned = padelTrainingService.updatePadelTrainingAthletes(id, athletes);
            return new ResponseEntity<PadelTraining>(padelTrainingReturned,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AppTrainerException e) {
        	return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @PostMapping("/{id}/athletes/{athlete_id}")
    public ResponseEntity<?> addAthlete(@PathVariable Integer id, @PathVariable Integer athlete_id) {
        try {
        	PadelTraining padelTrainingReturned = padelTrainingService.addAthlete(id, athlete_id);
            return new ResponseEntity<PadelTraining>(padelTrainingReturned,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AppTrainerException e) {
        	return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @DeleteMapping("/{id}/athletes/{athlete_id}")
    public ResponseEntity<?> deleteAthlete(@PathVariable Integer id, @PathVariable Integer athlete_id) {
        try {
        	PadelTraining padelTrainingReturned = padelTrainingService.deleteAthlete(id, athlete_id);
            return new ResponseEntity<PadelTraining>(padelTrainingReturned,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AppTrainerException e) {
        	return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }    
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

    	padelTrainingService.deletePadelTraining(id);
    }
}
