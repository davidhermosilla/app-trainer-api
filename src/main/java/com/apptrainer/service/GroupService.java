package com.apptrainer.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apptrainer.model.Group;
import com.apptrainer.repository.GroupRepository;
@Service
@Transactional
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    
    public List<Group> listAll() {
        return groupRepository.findAll();
    }

    public void saveGroup(Group group) {
    	groupRepository.save(group);
    }

    public Group getGroup(Integer id) {
        return groupRepository.findById(id).get();
    }

    public void deleteGroup(Integer id) {
    	groupRepository.deleteById(id);
    }
}
