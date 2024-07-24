package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.service.ResourceAllocProcessService;
import com.sentrifugo.performanceManagement.vo.ReadIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("ResourceAllocProcess")
public class    ResourceAllocProcessController {

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
            resourceAllocProcess.setProcessStatus( (String) requestBody.get("processStatus"));
            resourceAllocProcess.setPMOReadStatus(false);
            resourceAllocProcess.setRmReadStatus(false);
            resourceAllocProcess.setPmReadStatus(false);
            resourceAllocProcess.setCreatedBy(((String) requestBody.get("createdBy")));
            resourceAllocProcess.setCreatedDate(new Date(System.currentTimeMillis()));
            resourceAllocProcess.setActive(true);

            ResourceAllocProcess result = resourceAllocProcessService.createResourceAllocProcess(resourceAllocProcess);

            return ResponseEntity.ok("Resource Allocation Process created successfully." + result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to initiate Resource Allocation Process: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProcess(@PathVariable Long id, @RequestBody Map<String, ?> requestbody) throws ParseException {
        return resourceAllocProcessService.updateStatus(id, requestbody);
    }

    @PutMapping("/read")
    public ResponseEntity<?> markAsRead( @RequestBody ReadIDs requestBody){
        try{
            for(Long id: requestBody.getIds()){
                resourceAllocProcessService.markProcessAsRead(id,requestBody.getRole());
            }
            return ResponseEntity.ok("Marked as Read successfully");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to mark as read: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProcess(@PathVariable Long id){
        ResourceAllocProcess process = resourceAllocProcessService.getById(id);
        if(process !=null){
            resourceAllocProcessService.deleteProcess(id);
            return ResponseEntity.ok("Process deleted successfully");
        }else {
            return ResponseEntity.badRequest().body("Invalid id");
        }

    }

}
