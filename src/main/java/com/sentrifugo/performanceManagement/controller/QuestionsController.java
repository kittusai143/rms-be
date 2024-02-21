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

import java.util.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/questions")
@CrossOrigin
public class QuestionsController {

    @Autowired
    private AppraisalConfigRepository apconfigRepo;
    @Autowired
    private QuestionsService questionsService;
    @Autowired
    private ConfigRepo configRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @GetMapping("/get")
    public List<Questions> getAllQuestions() {
        return questionsRepository.findAll();
    }

    @GetMapping("/getConfig/{configId}")
    public ResponseEntity<?> getQuestionsByConfigId(@PathVariable("configId") Integer configId) {
        try {
            List<Questions> questions = questionsRepository.findByConfigId(configId);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            String errorMessage = "An error occurred while fetching questions by config ID.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping("add")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> postQuestions(@RequestBody Map<String, Object> requestBody) {
        try {
            String name = (String) requestBody.get("name");
            int createdBy = (int) requestBody.get("createdby");
            Config config = new Config();
            config.setConfigName(name);
            config.setCreatedBy(createdBy);
            config.setCreatedDate(new Date());
            config.setUpdatedBy(createdBy);
            Config savedConfig = configRepository.save(config);
            Integer configId = savedConfig.getId();
            List<Questions> addedQuestions = new ArrayList<>();
            List<Map<String, String>> questionsData = (List<Map<String, String>>) requestBody.get("questionsdata");
            for (Map<String, String> questionData : questionsData) {
                Questions question = new Questions();
                question.setParameter(questionData.get("parameter"));
                question.setQuestion(questionData.get("question"));
                question.setDescription(questionData.get("description"));
                question.setConfigId(configId);
                questionsRepository.save(question);
                addedQuestions.add(question);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("config", savedConfig);
            response.put("questions", addedQuestions);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updateConfig/{id}")
    public ResponseEntity<?> updateConfigById(@PathVariable("id") Integer configId, @RequestBody Map<String, Object> requestBody) {
        try {
            List<Map<String, Object>> questionsData = (List<Map<String, Object>>) requestBody.get("questionsdata");

            // Get existing questions associated with the given configId
            List<Questions> existingQuestions = questionsRepository.findByConfigId(configId);

            // Update or add questions based on the provided data
            for (Map<String, Object> questionData : questionsData) {
                Integer questionId = (Integer) questionData.get("id");
                String parameter = (String) questionData.get("parameter");
                String questionText = (String) questionData.get("question");
                String description = (String) questionData.get("description");

                // Find the existing question with the given ID, if any
                Questions existingQuestion = null;
                if (questionId != null) {
                    existingQuestion = existingQuestions.stream()
                            .filter(q -> q.getId().equals(questionId))
                            .findFirst()
                            .orElse(null);
                }

                if (existingQuestion != null) {
                    // Update existing question
                    existingQuestion.setParameter(parameter);
                    existingQuestion.setQuestion(questionText);
                    existingQuestion.setDescription(description);
                    questionsRepository.save(existingQuestion);
                    // Remove the updated question from the list of existing questions
                    existingQuestions.remove(existingQuestion);
                } else {
                    // Create a new question since no ID is provided
                    Questions newQuestion = new Questions();
                    newQuestion.setConfigId(configId);
                    newQuestion.setParameter(parameter);
                    newQuestion.setQuestion(questionText);
                    newQuestion.setDescription(description);
                    questionsRepository.save(newQuestion);
                }
            }

            // Delete any remaining existing questions not included in the update
            for (Questions questionToDelete : existingQuestions) {
                questionsRepository.delete(questionToDelete);
            }

            return ResponseEntity.ok("Questions updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating questions: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestions(@PathVariable("id") Integer id) {
        try {
            questionsRepository.deleteById(id);
            return ResponseEntity.ok("Question deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getlistofQns/{init_id}")
    public ResponseEntity<?> getQns(@PathVariable Integer init_id) {
        Integer id = apconfigRepo.getpid(init_id);
        List<Questions> questions = questionsRepository.getQuestionsByConfigId(id);
        if (questions.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "no record found for these id : " + init_id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        } else
            return ResponseEntity.ok(questions);

    }
}