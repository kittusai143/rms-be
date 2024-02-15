package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Questions;
import com.sentrifugo.performanceManagement.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionsService {

    @Autowired
    QuestionsRepository questionsRepository;

    public List<Questions> getAllQuestions() {
        return questionsRepository.findAll();
    }

    public List<Questions> addQuestions(List<Questions> questions) {
        return questionsRepository.saveAll(questions);

    }

    public String deleteQuestions(Integer id) {
        questionsRepository.deleteById(id);
        return "Deleted Successfully";
    }
}
