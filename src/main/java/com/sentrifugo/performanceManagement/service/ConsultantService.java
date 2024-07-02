package com.sentrifugo.performanceManagement.service;


import com.sentrifugo.performanceManagement.entity.ConsultantData;
import com.sentrifugo.performanceManagement.repository.ConsultantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultantService {

    @Autowired
    private ConsultantRepository consultantDataRepository;

    public List<ConsultantData> getAllConsultants() {
        return consultantDataRepository.findAll();
    }

    public ConsultantData addConsultant(ConsultantData consultantData) {
        return consultantDataRepository.save(consultantData);
    }

    public Optional<ConsultantData> getConsultantById(Integer consultantID) {
        return consultantDataRepository.findById(consultantID);
    }

    public ConsultantData updateConsultant(ConsultantData updatedConsultantData) {
        ConsultantData consultantData = consultantDataRepository.findById(updatedConsultantData.getConsultantID()).orElse(null);
        if (consultantData != null){
            return consultantDataRepository.save(consultantData);
        }
        return null;
    }

}
