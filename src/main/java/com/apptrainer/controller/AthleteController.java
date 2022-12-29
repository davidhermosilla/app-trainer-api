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
import com.apptrainer.model.TrainingHistory;
import com.apptrainer.service.AthleteService;

@RestController
@RequestMapping(AppTrainerConstant.APP_PREFIX+"/athletes")
public class AthleteController {
	
	static final Logger log = LoggerFactory.getLogger(AthleteController.class);
	
    @Autowired
    AthleteService athleteService;

    @GetMapping("/test")
    public  ResponseEntity<String> test() {
    	return new ResponseEntity<String>("Esto es un test", HttpStatus.OK);
    }    
    
    @GetMapping("")
    public List<Athlete> list() {
    	log.debug("List");
        return athleteService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Athlete> get(@PathVariable Integer id) {
        try {
            Athlete user = athleteService.getAthlete(id);
            return new ResponseEntity<Athlete>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Athlete>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/")
    public void add(@RequestBody Athlete user) {
        athleteService.saveAthlete(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Athlete user, @PathVariable Integer id) {
        try {
            user.setId(id);
            athleteService.saveAthlete(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/{athlete_id}/add-training/{training_id}")
    public ResponseEntity<?> addTraining(@RequestBody TrainingHistory training, @PathVariable Integer athlete_id, @PathVariable Integer training_id) {
        try {
            
        	List<TrainingHistory> listTraining = athleteService.addTrainingHistory(training_id, athlete_id, training);
        	
            return new ResponseEntity<>(listTraining,HttpStatus.OK);
        } catch (AppTrainerException ex) {
        	return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }    
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        athleteService.deleteAthlete(id);
    }
    
}
