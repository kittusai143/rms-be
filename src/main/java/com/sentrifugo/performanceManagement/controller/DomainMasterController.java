package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.DomainMaster;
import com.sentrifugo.performanceManagement.repository.DomainMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("domain")
public class DomainMasterController {
    @Autowired
    private DomainMasterRepository domainMasterRepository;
    @GetMapping("getAll")
    public List<DomainMaster> getAll(){
        return domainMasterRepository.findAll();
    }
    @GetMapping("/getBy/{id}")
    public DomainMaster getById(@PathVariable Integer id){
        return domainMasterRepository.findById(id).orElse(null);
    }
}
