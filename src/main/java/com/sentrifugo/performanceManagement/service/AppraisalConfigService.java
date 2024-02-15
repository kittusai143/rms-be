package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.Exceptions.ResourceNotFoundException;
import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import com.sentrifugo.performanceManagement.entity.Employee;
import com.sentrifugo.performanceManagement.repository.AppraisalConfigRepository;
import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppraisalConfigService {

    @Autowired
    private AppraisalConfigRepository appraisalConfigRepository;
    @Autowired
    private AppraisalMasterRepository appraisalMasterRepository;
    @Autowired
    private EmployeeService employeeService;
    public List<AppraisalConfig> getAllAppraisalConfig() {
        return appraisalConfigRepository.findAll();
    }

    public AppraisalConfig getAppraisalConfigById(Long appraisalConfigId) throws ResourceNotFoundException {
        return appraisalConfigRepository.findById(appraisalConfigId)
                .orElseThrow(() -> new ResourceNotFoundException("Appraisal Config data not found for this id " + appraisalConfigId));
    }

    public AppraisalConfig createAppraisalConfig(AppraisalConfig appraisalConfig) {
        AppraisalConfig savedAppraisalConfig = appraisalConfigRepository.save(appraisalConfig);
        createAppraisalMastersForEmployees(savedAppraisalConfig);
        return savedAppraisalConfig;
    }
    private void createAppraisalMastersForEmployees(AppraisalConfig appraisalConfig) {

        List<Employee> employees = employeeService.getEmployeesByBusinessUnitAndDepartmentAndRoleId(
                appraisalConfig.getBusinessUnit(), appraisalConfig.getDepartment(), appraisalConfig.getEnableTo());
        System.out.println(employees);

        for (Employee employee : employees) {
            Long empid = Long.valueOf(employee.getId());
            // Create a new AppraisalMaster object for each employee
            AppraisalMaster appraisalMaster = new AppraisalMaster();
            appraisalMaster.setEmployeeId(empid);
            appraisalMaster.setAppraisalIntitationId(appraisalConfig.getAppraisalConfigId());
            appraisalMaster.setPeriod(appraisalConfig.getPeriod());
            appraisalMaster.setCreatedBy(appraisalConfig.getCreatedBy());
            appraisalMaster.setCreatedDate(new Date());
            appraisalMaster.setActive(true);
            appraisalMaster.setStatus(appraisalConfig.getStatus());

            appraisalMasterRepository.save(appraisalMaster);

            // Update existing AppraisalMaster records for the same employee to set isActive to false
            List<AppraisalMaster> existingRecords = appraisalMasterRepository.findByEmployeeIdAndActive(Long.valueOf(employee.getId()), true);
            System.out.println(existingRecords);
            for (AppraisalMaster existingRecord : existingRecords) {
                existingRecord.setActive(false);
                appraisalMasterRepository.save(existingRecord);
            }
        }
    }



    public AppraisalConfig updateAppraisalConfig(Long appraisalConfigId, AppraisalConfig appraisalConfigdetails) throws ResourceNotFoundException {
        AppraisalConfig appraisalConfig = appraisalConfigRepository.findById(appraisalConfigId)
                .orElseThrow(() -> new ResourceNotFoundException("Appraisal configs not found for this id :: " + appraisalConfigId));
        // Update the fields
        appraisalConfig.setAppraisalMode(appraisalConfigdetails.getAppraisalMode());
        appraisalConfig.setWorkflowConfigId(appraisalConfigdetails.getWorkflowConfigId());
        appraisalConfig.setDepartment(appraisalConfigdetails.getDepartment());
        appraisalConfig.setEligibility(appraisalConfigdetails.getEligibility());
        appraisalConfig.setBusinessUnit(appraisalConfigdetails.getBusinessUnit());
        appraisalConfig.setEmployeeDueDate(appraisalConfigdetails.getEmployeeDueDate());
        appraisalConfig.setEnableTo(appraisalConfigdetails.getEnableTo());
        appraisalConfig.setManagerDueDate(appraisalConfigdetails.getManagerDueDate());
        appraisalConfig.setFromYear(appraisalConfigdetails.getFromYear());
        appraisalConfig.setParameter(appraisalConfigdetails.getParameter());
        appraisalConfig.setRating(appraisalConfigdetails.getRating());
        appraisalConfig.setPeriod(appraisalConfigdetails.getPeriod());
        appraisalConfig.setProcessStatus(appraisalConfigdetails.getProcessStatus());
        appraisalConfig.setToYear(appraisalConfigdetails.getToYear());
        appraisalConfig.setStatus(appraisalConfigdetails.getStatus());
        appraisalConfig.setCreatedBy(appraisalConfigdetails.getCreatedBy());

        return appraisalConfigRepository.save(appraisalConfig);
    }

    public void deleteAppraisalConfig(Long appraisalConfigId) throws ResourceNotFoundException {
        AppraisalConfig appraisalConfig = appraisalConfigRepository.findById(appraisalConfigId)
                .orElseThrow(() -> new ResourceNotFoundException("Appraisal configs not found for this id :: " + appraisalConfigId));
        appraisalConfigRepository.delete(appraisalConfig);
    }

}
