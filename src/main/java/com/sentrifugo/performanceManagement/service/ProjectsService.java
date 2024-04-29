package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Projects;
import com.sentrifugo.performanceManagement.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectsService {

    @Autowired
    public ProjectsRepository projectsRepository;

    public List<Projects> getAllProjects(){
        return projectsRepository.findAll();
    }

    public  Projects getProjectById(Long id){
        Optional<Projects> optionalProjects= projectsRepository.findById(id);
        return optionalProjects.orElse(null);
    }
}
