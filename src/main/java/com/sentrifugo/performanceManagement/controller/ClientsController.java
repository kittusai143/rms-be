package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Clients;
import com.sentrifugo.performanceManagement.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("clients")
public class ClientsController {

    @Autowired
    public ClientsService clientsService;

    @GetMapping("/getAll")
    public List<Map<String,?>> getAllClients(){
        return clientsService.getAllClients();
    }

}
