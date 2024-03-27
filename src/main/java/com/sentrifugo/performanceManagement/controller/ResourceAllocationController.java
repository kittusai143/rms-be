package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.service.ResourceAllocationService;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("ResAlloc")
public class ResourceAllocationController {

    @Autowired
    public ResourceAllocationService resourceAllocationService;

    @GetMapping("/getAll")
    public List<ResourceAllocation> getallResourceAllocation(){
        return resourceAllocationService.getAllResourceAllocations();
    }

    @PostMapping("/filter")
    public List<ResourceAllocation> filterResourceAllocations(@RequestBody ResourceAllocFilters filterRequest) {
       return resourceAllocationService.filterResourceAllocations(filterRequest);

    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<ResourceAllocation> updateResourceAllocation(@PathVariable Long id, @RequestBody Map<String,?> updatedAllocation) {
//        ResourceAllocation allocation = resourceAllocationService.updateResourceAllocation(id, updatedAllocation);
//        if (allocation != null) {
//            return ResponseEntity.ok(allocation);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

}
