

package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.repository.TrainingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@Component
public class TrainingDetailsService {
    @Autowired
    private TrainingDetailsRepository trainingDetailsRepository;
    public List<Map<String, Integer>> getByTrainingName(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return trainingDetailsRepository.getByTrainingName(startDate,endDate);
    }

    public List<Map<String, Integer>> getByTrainingArea(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return trainingDetailsRepository.getByTrainingArea(startDate,endDate);

    }

    public List<Map<String, Integer>> getTotalTrainingHrs(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return trainingDetailsRepository.getTotalTrainingHrs(startDate,endDate);

    }

    public List<Map<String, Integer>> getQuarterlyData(String year) {
        return trainingDetailsRepository.getQuarterlyData(year);
    }

    public List<Map<String, Integer>> getQuarterlyNominatedData(String year) {
        return trainingDetailsRepository.getQuarterlyNominatedData(year);
    }

    public List<Map<String, Integer>> getQuarterlyTechAreaData(String year) {
        return trainingDetailsRepository.getQuarterlyTechAreaData(year);
    }
}
