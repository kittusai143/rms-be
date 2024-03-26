package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import com.sentrifugo.performanceManagement.repository.ResourceAllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResourceAllocationService {

    @Autowired
    public ResourceAllocationRepository resourceAllocationRepository;

    public List<ResourceAllocation> getAllResourceAllocations(){
      return resourceAllocationRepository.findAll();
    }

    public ResourceAllocation getById(Long id){ return resourceAllocationRepository.findById(id).get();}
    public List<ResourceAllocation> findByLocation(List<String> location) {
        return resourceAllocationRepository.findByLocation(location);
    }

    public List<ResourceAllocation> findBySkills(List<String> skills) {
        List<ResourceAllocation> resultList = new ArrayList<>();
        for (String skill : skills) {
            List<ResourceAllocation> bySkill = resourceAllocationRepository.findBySkill(skill);
            resultList.addAll(bySkill);
        }
        return resultList;
    }
    public List<ResourceAllocation> findByLocationAndSkills(List<String> locations, List<String> skills) {
        List<ResourceAllocation> resultList = new ArrayList<>();
        for (String skill : skills) {
            List<ResourceAllocation> byLocationAndSkill = resourceAllocationRepository.findByLocationAndSkill(locations, skill);
            resultList.addAll(byLocationAndSkill);
        }
        return resultList;
    }
    public List<ResourceAllocation> findByLocationAndSkillsAndBillability(List<String> locations, List<String> skills, List<String> billabilities) {
        List<ResourceAllocation> resultList = new ArrayList<>();
        for (String skill : skills) {
            List<ResourceAllocation> byLocationAndSkillAndBillability = resourceAllocationRepository.findByLocationAndSkillAndBillability(locations, skill, billabilities);
            resultList.addAll(byLocationAndSkillAndBillability);
        }
        return resultList;
    }

    public List<ResourceAllocation> findByLocationAndBillability(List<String> locations, List<String> billabilities) {
        return resourceAllocationRepository.findByLocationAndBillability(locations,billabilities);
    }


    public List<ResourceAllocation> findBySkillsAndBillability(List<String> skills, List<String> billabilities) {
        List<ResourceAllocation> resultList = new ArrayList<>();
        for (String skill : skills) {
            List<ResourceAllocation> bySkillAndBillability = resourceAllocationRepository.findBySkillAndBillability(skill, billabilities);
            resultList.addAll(bySkillAndBillability);
        }
        return resultList;
    }

    public List<ResourceAllocation> findByBillability(List<String> billabilities) {
        return resourceAllocationRepository.findByBillability(billabilities);
    }

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
