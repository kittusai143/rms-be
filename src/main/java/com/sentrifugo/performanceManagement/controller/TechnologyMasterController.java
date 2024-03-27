package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.TechnologyMaster;
import com.sentrifugo.performanceManagement.repository.TechnologyMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("techgroup")
public class TechnologyMasterController {
    @Autowired
    private TechnologyMasterRepository repository;
    @GetMapping("getAll")
    public List<TechnologyMaster> getAll(){
        return repository.findAll();
    }
    @GetMapping("getBy/{id}")
    public TechnologyMaster getById(@PathVariable Integer id){
        return repository.findById(id).orElse(null);
    }

}
