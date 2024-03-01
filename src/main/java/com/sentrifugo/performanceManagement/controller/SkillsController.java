package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.Skills;
import com.sentrifugo.performanceManagement.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "http://localhost:3000")
public class SkillsController {

    @Autowired
    private SkillsService skillsService;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<Skills> getAllSkills() {
        return skillsService.getAllSkills();
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public List<Skills> addSkills(@RequestBody List<Skills> skills){
        return skillsService.addSkills(skills);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public String deleteSkills(@PathVariable ("id")Integer id){
        return skillsService.deleteSkills(id);


    }
}
