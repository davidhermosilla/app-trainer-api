package com.apptrainer.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apptrainer.model.TrainingHistory;
import com.apptrainer.repository.TrainingHistoryRepository;
@Service
@Transactional
public class TrainingHistoryService {
    @Autowired
    private TrainingHistoryRepository trainingHistoryRepository;

    public List<TrainingHistory> listAll() {
        return trainingHistoryRepository.findAll();
    }

    public TrainingHistory getTrainingHistory(Integer id) {
        return trainingHistoryRepository.findById(id).get();
    }

    public void deleteTrainingHistory(Integer id) {
    	trainingHistoryRepository.deleteById(id);
    }

}
