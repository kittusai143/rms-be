package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Clients;
import com.sentrifugo.performanceManagement.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientsService {

    @Autowired
    public ClientsRepository clientsRepository;

    public List<Clients> getAllClients(){
        return clientsRepository.findAll();
    }

}
