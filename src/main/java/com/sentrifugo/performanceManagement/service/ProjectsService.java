package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Projects;
import com.sentrifugo.performanceManagement.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Projects add(Projects project) {
        return projectsRepository.save(project);
    }
    public Projects update(Projects updatedProject) {
        Optional<Projects> project=projectsRepository.findById(updatedProject.getId());
        if(project.isPresent()){
            return projectsRepository.save(updatedProject);
        }
        else {
            return null;
        }
    }
    public Map<String,Long> getCount()
    {
        Long a= projectsRepository.count();
        //  Long b=projectsRepository.getStatusCount();
        Map<String,Long> map=new HashMap<>();
        map.put("TotalProject",a);
        //  map.put("Active",b);
        return map;

    }
}
