package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.Questions;
import com.sentrifugo.performanceManagement.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")

public class QuestionsController {

    @Autowired
    private QuestionsService questionsService;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<Questions> getAllQuestions() {
        return questionsService.getAllQuestions();
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public List<Questions> addQuestions(@RequestBody List<Questions> questions){
        return questionsService.addQuestions(questions);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public String deleteQuestions(@PathVariable ("id")Integer id){
        return questionsService.deleteQuestions(id);


    }
}