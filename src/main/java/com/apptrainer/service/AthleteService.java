package com.apptrainer.service;

import com.apptrainer.model.Athlete;
import com.apptrainer.repository.AthleteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class AthleteService {
    @Autowired
    private AthleteRepository userRepository;
    public List<Athlete> listAllUser() {
        return userRepository.findAll();
    }

    public void saveUser(Athlete user) {
        userRepository.save(user);
    }

    public Athlete getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
