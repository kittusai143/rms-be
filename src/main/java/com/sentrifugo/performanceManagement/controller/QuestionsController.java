package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.Config;
import com.sentrifugo.performanceManagement.entity.Questions;
import com.sentrifugo.performanceManagement.repository.AppraisalConfigRepository;
import com.sentrifugo.performanceManagement.repository.ConfigRepo;
import com.sentrifugo.performanceManagement.repository.QuestionsRepository;
import com.sentrifugo.performanceManagement.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")

public class QuestionsController {

    @Autowired
    private AppraisalConfigRepository apconfigRepo;
    @Autowired
    private QuestionsService questionsService;
    @Autowired
    private ConfigRepo configRepository;
    @Autowired
    private QuestionsRepository questionsRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<Questions> getAllQuestions() {
        return questionsService.getAllQuestions();
    }

    @PostMapping("add")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> postQuestions(@RequestBody Map<String, Object> requestBody) {

        try {
            // Extracting configuration data from the request body
            String name = (String) requestBody.get("name");

            int createdBy = (int) requestBody.get("createdby");

            // Save configuration data into appraisal_config table
            Config config = new Config();
//            config.setId(9);
            config.setConfigName(name);
            config.setCreatedBy(createdBy);
            config.setCreatedDate(new Date());
            config.setUpdatedBy(createdBy);
            Config savedConfig = configRepository.save(config);
            // Get the newly generated config_id
            Integer configId = savedConfig.getId();

            // Extracting questions data from the request body
            List<Map<String, String>> questionsData = (List<Map<String, String>>) requestBody.get("questionsdata");
            // Save each question with the corresponding config_id
            for (Map<String, String> questionData : questionsData) {
                Questions question = new Questions();
                question.setParameter(questionData.get("parameter"));
                question.setQuestion(questionData.get("question"));
                question.setDescription(questionData.get("description"));
                question.setConfigId(configId);
                questionsRepository.save(question);

            }

            return ResponseEntity.status(HttpStatus.CREATED).body("Questions added successfully.");
        } catch (Exception e) {
            String errorMessage = "An error occurred while saving questions.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public String deleteQuestions(@PathVariable("id") Integer id) {
        return questionsService.deleteQuestions(id);
    }

    @GetMapping("/getlistofQns/{init_id}")
    public ResponseEntity<?> getQns(@PathVariable Integer init_id){
            Integer id=apconfigRepo.getpid(init_id);
            List<Questions> questions=questionsRepository.getQuestionsByConfigId(id);
            if(questions.isEmpty())
            {
                String message="NO Record found";
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            }
            else
                return ResponseEntity.ok(questions);
    }
}