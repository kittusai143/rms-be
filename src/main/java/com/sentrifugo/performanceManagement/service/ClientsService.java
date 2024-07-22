package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Clients;
import com.sentrifugo.performanceManagement.repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientsService {

    @Autowired
    public ClientsRepository clientsRepository;

    public List<Map<String,?>> getAllClients(){
        return clientsRepository.findAllDistinct("Active");

    }
    public List<Clients>  pmogetAllClients(){
        return clientsRepository.findAll();
    }
    public String addclient(Clients client) {
        clientsRepository.save(client);
        return "added";
    }

    public Object updateclient(Clients clients) {
        Map<String,String> map=new HashMap<>();
        if(clientsRepository.existsById(clients.getId())){
            return clientsRepository.save(clients);}
        else
            map.put("message","Id-not-Found");
        return map;
    }


    public boolean deleteclient(Long id) {
        if(clientsRepository.existsById(id)){
            clientsRepository.deleteById(id);
            return true;
        }
        else return false;
    }


    public List<String> getAllClientIds() {
        return clientsRepository.findAllClientIds();
    }

    public List<Object> getAllClientNames() {
        return clientsRepository.getAllClientNames();
    }

    public List<Object> getAllDistinctClientNames() {
        return clientsRepository.getDistinctClientNames();
    }
}
