package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.TechnologyData;
import com.sentrifugo.performanceManagement.repository.TechnologyDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("skills")
public class TechnologyDataController {
    @Autowired
    private TechnologyDataRepository technologyDataRepository;
    @GetMapping("getAll")
    public List<TechnologyData> getAll(){
        return technologyDataRepository.findAll();
    }
    @GetMapping("/getBy/{id}")
    public TechnologyData getBYId(@PathVariable Integer id){
        return technologyDataRepository.findById(id).orElse(null);
    }

}
