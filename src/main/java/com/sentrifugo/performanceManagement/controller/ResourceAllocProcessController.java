package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.service.ResourceAllocProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("ResourceAllocProcess")
public class ResourceAllocProcessController {

    @Autowired
    public ResourceAllocProcessService resourceAllocProcessService;

    @GetMapping("getAll")
    public  List<ResourceAllocProcess> getAll(){
       return resourceAllocProcessService.getAll();
    }
    @GetMapping("/getlistusers")
    public List<Map<String,Object>> getResourceAllocProcessAndUsers() {
        return resourceAllocProcessService.getResourceAllocProcessAndUsers();
    }

    @GetMapping("/byId/{id}")
    public ResourceAllocProcess getById(@PathVariable long id){
        return resourceAllocProcessService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String, ?> requestBody){
        try {
            ResourceAllocProcess resourceAllocProcess =new ResourceAllocProcess();
            resourceAllocProcess.setSilId( (String) requestBody.get("silId"));
            resourceAllocProcess.setResAllocId(((Integer) requestBody.get("resAllocId")).longValue());
            resourceAllocProcess.setProjectCode( (String) requestBody.get("projectCode"));
            resourceAllocProcess.setProcessStatus( (String) requestBody.get("processStatus"));
            if((String) requestBody.get("startDate")!=null && (String) requestBody.get("endDate") !=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                Date startDate = sdf.parse((String) requestBody.get("startDate"));
                Date endDate = sdf.parse((String) requestBody.get("endDate"));
                resourceAllocProcess.setStartDate(startDate);
                resourceAllocProcess.setEndDate(endDate);
            }
            resourceAllocProcess.setCreatedBy(((String) requestBody.get("createdBy")));
            resourceAllocProcess.setCreatedDate(new Date(System.currentTimeMillis()));
            resourceAllocProcess.setActive(true);

            ResourceAllocProcess result = resourceAllocProcessService.createResourceAllocProcess(resourceAllocProcess);

            return ResponseEntity.ok("Resource Allocation Process created successfully." + result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to initiate Resource Allocation Process: " + e.getMessage());
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateProcess(@PathVariable Long id, @RequestBody Map<String, ?> requestbody) throws ParseException {

        ResourceAllocProcess allocation = resourceAllocProcessService.updateStatus(id, requestbody);
        if (allocation != null) {
            return ResponseEntity.ok(allocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
