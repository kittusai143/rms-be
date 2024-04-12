package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.ProjectAllocation;
import com.sentrifugo.performanceManagement.service.ProjectAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("projectAlloc")
public class ProjectAllocationController {
    @Autowired
    private ProjectAllocationService projectAllocationService;
    @GetMapping("getAll")
    public List<ProjectAllocation> getAllProjectAllocations(){
        return projectAllocationService.getAllProjectAllocations();
    }

    @PostMapping("create")
    public ResponseEntity<?> createProjectAllocation(@RequestBody Map<String, ?> request){
        try{
            ProjectAllocation projectAllocation = new ProjectAllocation();
            projectAllocation.setResAllocId( ((Integer) request.get("resAllocId")).longValue() );
            projectAllocation.setProjectCode( (String) request.get("projectCode"));
            if((String) request.get("startDate")!=null && (String) request.get("endDate") !=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T");
                Date startDate = sdf.parse((String) request.get("startDate"));
                Date endDate = sdf.parse((String) request.get("endDate"));
                    projectAllocation.setStartDate(startDate);
                   projectAllocation.setEndDate(endDate);
            }
            projectAllocation.setCreatedBy((String) request.get("createdBy"));
            projectAllocation.setCreatedDate(new Date(System.currentTimeMillis()));
            projectAllocation.setActive(true);
            ProjectAllocation saved = projectAllocationService.createProjectAllocation(projectAllocation);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to allocate project: " + e.getMessage());
        }
    }
}
