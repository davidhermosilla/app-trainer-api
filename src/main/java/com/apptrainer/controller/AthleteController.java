package com.apptrainer.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import com.apptrainer.response.PaysResponse;
import com.apptrainer.service.AthleteService;
import com.apptrainer.service.TrainingHistoryService;
import com.apptrainer.service.util.AppTrainerUtil;
import com.apptrainer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(AppTrainerConstant.APP_PREFIX+"/athletes")
public class AthleteController {
	// TODO: Añadir control de excepciones
	static final Logger log = LoggerFactory.getLogger(AthleteController.class);
	
	@Autowired
	private MessageSource mensajes;
	
    @Autowired
    AthleteService athleteService;
    
    @Autowired
    TrainingHistoryService trainingHistoryService;

    @GetMapping("/test")
    public  ResponseEntity<String> test() {
    	return new ResponseEntity<String>(AppTrainerUtil.getString(mensajes,"language.test"), HttpStatus.OK);
    }    
    
    @GetMapping("")
    @JsonView(View.Basic.class)
    public List<Athlete> list() {
    	log.debug("List");
        return athleteService.listAll();
    }

    @GetMapping("/{id}")
    @JsonView(View.Extended.class)
    public ResponseEntity<Athlete> get(@PathVariable Integer id) {
        try {
            Athlete user = athleteService.getAthlete(id);
            return new ResponseEntity<Athlete>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Athlete>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/")
    @JsonView(View.Basic.class)
    public Athlete add(@RequestBody Athlete user) {
    	return athleteService.saveAthlete(user);
    }
    
    @PutMapping("/{id}")
    @JsonView(View.Basic.class)
    public ResponseEntity<?> update(@RequestBody Athlete user, @PathVariable Integer id) {
        try {
            user.setId(id);
            athleteService.saveAthlete(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{athlete_id}/pending-pays")
    @JsonView(View.Basic.class)
    public ResponseEntity<?> getPendingPays(@PathVariable Integer athlete_id) {
    	PaysResponse response;
		try {
			response = athleteService.getPendingPays(athlete_id);
		
			return new ResponseEntity<>(response,HttpStatus.OK);
		} catch (Exception ex) {
        	return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }    
    
    @PostMapping("/{athlete_id}/training-histories/{training_id}")
    @JsonView(View.Extended.class)
    public ResponseEntity<?> addTraining(@RequestBody TrainingHistory training, @PathVariable Integer athlete_id, @PathVariable Integer training_id) {
    	Athlete athlete=null;
        try {
            if (training.getRepeat_training()!=0) {
            	athlete = athleteService.addTrainingsHistory(training_id, athlete_id, training,training.getRepeat_training());
            } else {
            	athlete = athleteService.addTrainingHistory(training_id, athlete_id, training);
            }
        	
            return new ResponseEntity<>(athlete,HttpStatus.OK);
        } catch (AppTrainerException ex) {
        	return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @DeleteMapping("/{athlete_id}/training-histories/{training_history_id}")
    @JsonView(View.Extended.class)
    public void deleteTraining(@PathVariable Integer athlete_id, @PathVariable Integer training_history_id) {
        trainingHistoryService.deleteTrainingHistory(training_history_id);
    } 
    
    @PutMapping("/{athlete_id}/training-histories/{training_history_id}/status")
    @JsonView(View.Extended.class)
    public void modifyTrainingStatus(@RequestBody TrainingHistory training,@PathVariable Integer athlete_id, @PathVariable Integer training_history_id) {
        trainingHistoryService.modifyTrainingHistoryStatus(training_history_id,training);
    }
    
    @PutMapping("/{athlete_id}/training-histories/{training_history_id}/training-date")
    @JsonView(View.Extended.class)
    public void modifyTrainingDate(@RequestBody TrainingHistory training,@PathVariable Integer athlete_id, @PathVariable Integer training_history_id) {
        trainingHistoryService.modifyTrainingHistoryDate(training_history_id,training);
    }     
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        athleteService.deleteAthlete(id);
    }

	public MessageSource getMensajes() {
		return mensajes;
	}

	public void setMensajes(MessageSource mensajes) {
		this.mensajes = mensajes;
	}
    
}
