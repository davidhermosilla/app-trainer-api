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
import com.apptrainer.model.Group;
import com.apptrainer.service.GroupService;
import com.apptrainer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(AppTrainerConstant.APP_PREFIX+"/groups")
public class GroupController {
	
	static final Logger log = LoggerFactory.getLogger(GroupController.class);
	
    @Autowired
    GroupService groupService;

    @GetMapping("")
    @JsonView(View.Basic.class)
    public List<Group> list() {
    	log.debug("List All Padel Trainings");
        return groupService.listAll();
    }

    @GetMapping("/{id}")
    @JsonView(View.Group.class)
    public ResponseEntity<Group> get(@PathVariable Integer id) {
        try {
        	Group Group = groupService.getGroup(id);
            return new ResponseEntity<Group>(Group, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Group>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/")
    @JsonView(View.Basic.class)
    public ResponseEntity<Group> add(@RequestBody Group group) {
    	group = groupService.saveGroup(group);
    	return new ResponseEntity<Group>(group,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @JsonView(View.Basic.class)
    public ResponseEntity<Group> update(@RequestBody Group group, @PathVariable Integer id) {
        try {
        	group.setId(id);
        	group = groupService.saveGroup(group);
            return new ResponseEntity<Group>(group,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/{id}/athletes/{athlete_id}")
    @JsonView(View.Group.class)
    public ResponseEntity<?> addAthlete(@PathVariable Integer id, @PathVariable Integer athlete_id) {
        try {
        	Group groupReturned = groupService.addAthlete(id, athlete_id);
            return new ResponseEntity<Group>(groupReturned,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AppTrainerException e) {
        	return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @DeleteMapping("/{id}/athletes/{athlete_id}")
    @JsonView(View.Group.class)
    public ResponseEntity<?> deleteAthlete(@PathVariable Integer id, @PathVariable Integer athlete_id) {
        try {
        	Group GroupReturned = groupService.deleteAthlete(id, athlete_id);
            return new ResponseEntity<Group>(GroupReturned,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AppTrainerException e) {
        	return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }    
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

    	groupService.deleteGroup(id);
    }
}
