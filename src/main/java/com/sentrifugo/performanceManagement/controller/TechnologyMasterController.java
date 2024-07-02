package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.TechnologyMaster;
import com.sentrifugo.performanceManagement.repository.TechnologyMasterRepository;
import com.sentrifugo.performanceManagement.service.TechnologyMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("techgroup")
public class TechnologyMasterController {
    @Autowired
    private TechnologyMasterService service;
    @Autowired
    private TechnologyMasterRepository repository;
    @GetMapping("getAll")
    public List<TechnologyMaster> getAll(){
        return repository.findAll();
    }

    @GetMapping("getDistinctGroups")
    public List<String> getDistinctGroups(){
        return repository.getDistinctGroups();
    }
    @GetMapping("getBy/{id}")
    public TechnologyMaster getById(@PathVariable Integer id){
        return repository.findById(id).orElse(null);
    }

    @PostMapping("/add")
    public TechnologyMaster addMaster(@RequestBody TechnologyMaster technologyMaster){
        return repository.save(technologyMaster);
    }

    @PutMapping("/update")
    public TechnologyMaster updateMaster(@RequestBody TechnologyMaster technologyMaster){
        return service.update(technologyMaster);
    }

    @GetMapping("getdistinct")
    public ResponseEntity<?> getDistinct(){
        try{
            Map<String,List<String>> distinct= service.getDistinctGroupsandSkills();
            return ResponseEntity.ok(distinct);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
