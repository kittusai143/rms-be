package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.ClientInformation;
import com.sentrifugo.performanceManagement.entity.Role;
import com.sentrifugo.performanceManagement.repository.ClientInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientInformationService {

    @Autowired
    public ClientInformationRepository clientInformationRepository;

    public List<ClientInformation> getAllClientInformation() {
        return clientInformationRepository.findAll();
    }

}
