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

import com.apptrainer.model.Athlete;
import com.apptrainer.model.PadelTraining;
import com.apptrainer.service.PadelTrainingService;

@RestController
@RequestMapping("/padeltrainings")
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
    public void add(@RequestBody PadelTraining padelTraining) {
    	padelTrainingService.savePadelTraining(padelTraining);
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
        }
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

    	padelTrainingService.deletePadelTraining(id);
    }
}
