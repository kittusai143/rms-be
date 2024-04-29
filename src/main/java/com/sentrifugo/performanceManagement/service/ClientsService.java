package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Clients;
import com.sentrifugo.performanceManagement.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClientsService {

    @Autowired
    public ClientsRepository clientsRepository;

    public List<Map<String,?>> getAllClients(){
        return clientsRepository.findAllDistinct();
    }

}
