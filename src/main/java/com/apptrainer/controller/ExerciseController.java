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
import com.apptrainer.model.Exercise;
import com.apptrainer.service.ExerciseService;
import com.apptrainer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(AppTrainerConstant.APP_PREFIX+"/exercises")
public class ExerciseController {
	//TODO: Añadir control de excepciones
	static final Logger log = LoggerFactory.getLogger(ExerciseController.class);
	
    @Autowired
    ExerciseService exerciseService;

    @GetMapping("")
    @JsonView(View.Basic.class)
    public List<Exercise> list() {
    	log.debug("List All Exercises");
        return exerciseService.listAllExcercises();
    }

    @GetMapping("/{id}")
    @JsonView(View.Extended.class)
    public ResponseEntity<?> get(@PathVariable Integer id) {
        try {
        	Exercise exercise = exerciseService.getExercise(id);
            return new ResponseEntity<Exercise>(exercise, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/")
    @JsonView(View.Basic.class)
    public Exercise add(@RequestBody Exercise exercise) {
    	return exerciseService.saveExercise(exercise);
    }

    @PutMapping("/{id}")
    @JsonView(View.Basic.class)
    public ResponseEntity<?> update(@RequestBody Exercise exercise, @PathVariable Integer id) {
        try {
        	exercise.setId(id);
        	exercise = exerciseService.saveExercise(exercise);
            return new ResponseEntity<Exercise>(exercise,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

    	exerciseService.deleteExercise(id);
    }
}
