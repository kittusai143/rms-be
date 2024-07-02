package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Clients;
import com.sentrifugo.performanceManagement.entity.Projects;
import com.sentrifugo.performanceManagement.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Projects project){
        try{
            Projects result=projectsService.add(project);

            if(result==null){
                Map<String,String > map=new HashMap<>();
                map.put("message","Error adding the project data");
                return ResponseEntity.ok(map);
            }
            else {
                return ResponseEntity.ok(result);
            }
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @PutMapping("/update")
    public  ResponseEntity<?> update(@RequestBody Projects updatedProject){
        try{
            Projects result=projectsService.update(updatedProject);
            if(result==null){
                Map<String,String > map=new HashMap<>();
                map.put("message","Error updating the project data");
                return ResponseEntity.ok(map);
            }
            else {
                return ResponseEntity.ok(result);
            }
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @GetMapping("/getCountOfProjects")
    public ResponseEntity<?> getCounts()
    {
        try {
            Map<String,Long> map=new HashMap<>();
            map=projectsService.getCount();
            if(map==null)
            {
                Map<String,String > m=new HashMap<>();
                m.put("message","No project data found");
                return ResponseEntity.ok(m);
            }
            else
                return ResponseEntity.ok(map);

        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);

        }
    }

}
