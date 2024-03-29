package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.service.ResourceAllocationService;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("ResAlloc")
public class ResourceAllocationController {

    @Autowired
    public ResourceAllocationService resourceAllocationService;

    @GetMapping("/getAll")
    public List<ResourceAllocation> getAllResourceAllocation(){ return resourceAllocationService.getAllResourceAllocations(); }

    @GetMapping("/getLocations")
    public List<String> getDistinctLocations(){ return resourceAllocationService.getDistinctLocations(); }

    @GetMapping("/getRoles")
    public List<String> getDistinctRoles(){ return resourceAllocationService.getDistinctRoles(); }

    @PostMapping("/filter")
    public List<ResourceAllocation> filterResourceAllocations(@RequestBody ResourceAllocFilters filterRequest) {
       return resourceAllocationService.filterResourceAllocations(filterRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResourceAllocation> updateResourceAllocation(@PathVariable Long id, @RequestBody Map<String,?> updatedAllocation) throws ParseException {
        ResourceAllocation allocation = resourceAllocationService.updateResourceAllocation(id, updatedAllocation);
        if (allocation != null) {
            return ResponseEntity.ok(allocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
