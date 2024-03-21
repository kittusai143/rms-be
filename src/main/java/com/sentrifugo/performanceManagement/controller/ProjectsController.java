package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Clients;
import com.sentrifugo.performanceManagement.entity.Projects;
import com.sentrifugo.performanceManagement.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("projects")
public class ProjectsController {

    @Autowired
    public ProjectsService projectsService;

    @GetMapping("/getAll")
    public List<Projects> getAllProjects(){
        return projectsService.getAllProjects();
    }

}
