package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.ProjectAllocation;
import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.repository.ProjectAllocationRepository;
import com.sentrifugo.performanceManagement.repository.ResourceAllocProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

public class ProjectAllocationService {
    @Autowired
    private ProjectAllocationRepository projectAllocationRepository;
    @Autowired
    private ResourceAllocProcessRepository resourceAllocProcessRepository;

    public List<ProjectAllocation> getAllProjectAllocations() {
        return projectAllocationRepository.findAll();
    }

    public ProjectAllocation createProjectAllocation(ProjectAllocation projectAllocation) {
        return projectAllocationRepository.save(projectAllocation);
    }

    public ResponseEntity<?> updateProjectAllocation(ProjectAllocation projectAllocation) {
        ProjectAllocation response= projectAllocationRepository.save(projectAllocation);
        return ResponseEntity.ok(projectAllocation);
    }

    public ProjectAllocation findByProcessId(Long id) {
        return projectAllocationRepository.findByAllocProcessId(id);
    }

    public List<ProjectAllocation> getByResourceAllocationId(Long resAllocId) {
        return projectAllocationRepository.findByResourceAllocId(resAllocId, true);
    }

    public List<Map<String ,?>> getAllProjectAllocationsBYResAllocID(Long id) {
        return projectAllocationRepository.findAllBYResAllocID(id);
    }
}
