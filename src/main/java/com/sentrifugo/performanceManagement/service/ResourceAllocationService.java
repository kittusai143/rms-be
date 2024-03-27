package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
import com.sentrifugo.performanceManagement.vo.ResourceAllocFilters;
import com.sentrifugo.performanceManagement.vo.ResourceAllocSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResourceAllocationService {

    @Autowired
    public ResourceAllocationRepository resourceAllocationRepository;


    public List<ResourceAllocation> filterResourceAllocations(ResourceAllocFilters criteria) {
        Specification<ResourceAllocation> spec = ResourceAllocSpecification.filterResourceAllocations(criteria);
        return resourceAllocationRepository.findAll(spec);
    }

    public List<ResourceAllocation> getAllResourceAllocations(){
      return resourceAllocationRepository.findAll();
    }

    public ResourceAllocation getById(Long id){ return resourceAllocationRepository.findById(id).get();}

//    public ResourceAllocation updateResourceAllocation(Long id, Map<String,?> updatedAllocation) {
//        Optional<ResourceAllocation> optionalAllocation = resourceAllocationRepository.findById(id);
//        if (optionalAllocation.isPresent()) {
//            ResourceAllocation allocation = optionalAllocation.get();
//
//            allocation.setRole(updatedAllocation.getRole());
//            allocation.setEmployeeType(updatedAllocation.getEmployeeType());
//            allocation.setDoj(updatedAllocation.getDoj());
//            allocation.setStatus(updatedAllocation.isStatus());
//            allocation.setPartner(updatedAllocation.getPartner());
//            allocation.setProjectName(updatedAllocation.getProjectName());
//            allocation.setProjectCode(updatedAllocation.getProjectCode());
//            allocation.setProjectType(updatedAllocation.getProjectType());
//            allocation.setProjectStartDate(updatedAllocation.getProjectStartDate());
//            allocation.setBillingStartDate(updatedAllocation.getBillingStartDate());
//            allocation.setBillingEndDate(updatedAllocation.getBillingEndDate());
//            allocation.setProjectEndDate(updatedAllocation.getProjectEndDate());
//            allocation.setSow(updatedAllocation.getSow());
//            allocation.setSowStartDate(updatedAllocation.getSowStartDate());
//            allocation.setSowEndDate(updatedAllocation.getSowEndDate());
//            allocation.setClientManager(updatedAllocation.getClientManager());
//            allocation.setBillability(updatedAllocation.getBillability());
//            allocation.setLocation(updatedAllocation.getLocation());
//            allocation.setClientTimesheetAccess(updatedAllocation.getClientTimesheetAccess());
//            allocation.setPartnerEmailID(updatedAllocation.getPartnerEmailID());
//            allocation.setClientEmailID(updatedAllocation.getClientEmailID());
//            allocation.setYubikey(updatedAllocation.getYubikey());
//            allocation.setYubikeySno(updatedAllocation.getYubikeySno());
//            allocation.setContactNumber(updatedAllocation.getContactNumber());
//            allocation.setGender(updatedAllocation.getGender());
//            allocation.setSkillset1(updatedAllocation.getSkillset1());
//            allocation.setSkillset2(updatedAllocation.getSkillset2());
//            allocation.setTraining(updatedAllocation.getTraining());
//            allocation.setCertifications(updatedAllocation.getCertifications());
//            allocation.setTechnologyDivision(updatedAllocation.getTechnologyDivision());
//            allocation.setAwards(updatedAllocation.getAwards());
//            allocation.setAudit(updatedAllocation.getAudit());
//
//            return resourceAllocationRepository.save(allocation);
//        } else {
//            return null;
//        }
//    }

}
