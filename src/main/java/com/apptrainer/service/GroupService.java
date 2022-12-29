package com.apptrainer.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apptrainer.exception.AppTrainerException;
import com.apptrainer.model.Athlete;
import com.apptrainer.model.Group;
import com.apptrainer.repository.AthleteRepository;
import com.apptrainer.repository.GroupRepository;
import com.apptrainer.service.util.AppTrainerUtil;
@Service
@Transactional
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    public List<Group> listAll() {
        return groupRepository.findAll();
    }

    public Group saveGroup(Group group) {
    	return groupRepository.save(group);
    }

    public Group getGroup(Integer id) {
        return groupRepository.findById(id).get();
    }

    public void deleteGroup(Integer id) {
    	groupRepository.deleteById(id);
    }

	public Group addAthlete(Integer id, Integer athlete_id) throws AppTrainerException {
		
		Group group = AppTrainerUtil.checkGroup(groupRepository, id);
		
		Athlete athlete = AppTrainerUtil.checkAthlete(athleteRepository, athlete_id);
		
		if(!group.getAthletes().contains(athlete)) {
			group.getAthletes().add(athlete);
		}
		
		groupRepository.save(group);
		
		return group;
	}


	public Group deleteAthlete(Integer id, Integer athlete_id) throws AppTrainerException {

		Group group = AppTrainerUtil.checkGroup(groupRepository,id);
		
		Athlete athlete = AppTrainerUtil.checkAthlete(athleteRepository, athlete_id);
		
		if(group.getAthletes().contains(athlete)) {
			group.getAthletes().remove(athlete);
		}
		
		groupRepository.save(group);
		
		return group;
	}
}
