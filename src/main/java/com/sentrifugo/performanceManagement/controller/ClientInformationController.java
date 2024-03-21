package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.ClientInformation;
import com.sentrifugo.performanceManagement.service.ClientInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("clientInfo")
public class ClientInformationController {

    @Autowired
    public ClientInformationService clientInformationService;
    @GetMapping("/getAll")
    public List<ClientInformation> getAllClientInformation() {
        return clientInformationService.getAllClientInformation();
    }


}
