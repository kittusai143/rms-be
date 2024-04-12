package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.ProjectAllocation;
import com.sentrifugo.performanceManagement.repository.ProjectAllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public class ProjectAllocationService {
    @Autowired
    private ProjectAllocationRepository projectAllocationRepository;

    public List<ProjectAllocation> getAllProjectAllocations() {
        return projectAllocationRepository.findAll();
    }

    public ProjectAllocation createProjectAllocation(ProjectAllocation projectAllocation) {
        return projectAllocationRepository.save(projectAllocation);
    }
}
