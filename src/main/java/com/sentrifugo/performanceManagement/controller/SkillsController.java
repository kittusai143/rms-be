package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.Skills;
import com.sentrifugo.performanceManagement.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "${custom.frontendUrl}")
public class SkillsController {

    @Autowired
    private SkillsService skillsService;

    @GetMapping("/get")
    public List<Skills> getAllSkills() {
        return skillsService.getAllSkills();
    }

    @PostMapping("/add")
    public List<Skills> addSkills(@RequestBody List<Skills> skills){
        return skillsService.addSkills(skills);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSkills(@PathVariable ("id")Integer id){
        return skillsService.deleteSkills(id);


    }
}
