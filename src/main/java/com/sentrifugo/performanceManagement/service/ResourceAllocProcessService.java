package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.ResourceAllocProcess;
import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.repository.ResourceAllocProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class ResourceAllocProcessService {

    @Autowired
    public ResourceAllocProcessRepository resourceAllocProcessRepository;


    public ResourceAllocProcess getById(long id) {
        return resourceAllocProcessRepository.findById(id).get();
    }

    public ResourceAllocProcess createResourceAllocProcess(ResourceAllocProcess resourceAllocProcess) {
        return resourceAllocProcessRepository.save(resourceAllocProcess);
    }


    public ResourceAllocProcess updateStatus(Long id, Map<String, ?> requestBody) {
        Optional<ResourceAllocProcess> optionalAllocation = resourceAllocProcessRepository.findById(id);
        if (optionalAllocation.isPresent()) {
            ResourceAllocProcess allocation = optionalAllocation.get();

            allocation.setProcessStatus((String) requestBody.get("processStatus"));
            allocation.setUpdatedBy( ((Integer) requestBody.get("updatedBy")).longValue());
            allocation.setUpdatedDate(new Date(System.currentTimeMillis()));

            return resourceAllocProcessRepository.save(allocation);
        } else {
            return null;
        }
    }
}
