package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.Exceptions.ResourceNotFoundException;
import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
import com.sentrifugo.performanceManagement.entity.AppraisalEmpHistory;
import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import com.sentrifugo.performanceManagement.entity.Employee;
import com.sentrifugo.performanceManagement.repository.AppraisalConfigRepository;
import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
//import com.sentrifugo.performanceManagement.vo.EmailController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppraisalConfigService {
//    @Autowired
//    private EmailController control;
    @Autowired
    private AppraisalConfigRepository appraisalConfigRepository;
    @Autowired
    private AppraisalMasterRepository appraisalMasterRepository;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AppraisalEmpHistoryService appraisalEmpHistoryService;

    public List<AppraisalConfig> getAllAppraisalConfig() {
        return appraisalConfigRepository.findAll();
    }

    public AppraisalConfig getAppraisalConfigById(Long appraisalConfigId) throws ResourceNotFoundException {
        return appraisalConfigRepository.findById(appraisalConfigId)
                .orElseThrow(() -> new ResourceNotFoundException("Appraisal Config data not found for this id " + appraisalConfigId));
    }

    public AppraisalConfig createAppraisalConfig(AppraisalConfig appraisalConfig) {
        AppraisalConfig savedAppraisalConfig = appraisalConfigRepository.save(appraisalConfig);
        System.out.println("Saved the config : "+savedAppraisalConfig);
        createAppraisalMastersForEmployees(savedAppraisalConfig);
        return savedAppraisalConfig;

    }



    private void createAppraisalMastersForEmployees(AppraisalConfig appraisalConfig) {
        System.out.println("Creating master......."+appraisalConfig);

        // Split the comma-separated departments into a list
        List<String> departments = Arrays.asList(appraisalConfig.getDepartment().split(", "));

        // Get the employees with the given role ID in the specified departments and business unit
        List<Employee> employees = employeeService.getEmployeesByBusinessUnitAndDepartmentsAndRoleIdAndDateOfJoining(
                appraisalConfig.getBusinessUnit(), departments, appraisalConfig.getEnableTo(), appraisalConfig.getCutOffDate());

        System.out.println(employees);

        // Iterate over the employees
        for (Employee employee : employees) {
            // Update existing AppraisalMaster records for the same employee to set isActive to false
            List<AppraisalMaster> existingRecords = appraisalMasterRepository.findByEmployeeIdAndActive(Long.valueOf(employee.getId()), true);
            for (AppraisalMaster existingRecord : existingRecords) {
                existingRecord.setActive(false);
                appraisalMasterRepository.save(existingRecord);
            }
            Long empid = Long.valueOf(employee.getId());
            // Create a new AppraisalMaster object for each employee
            AppraisalMaster appraisalMaster = new AppraisalMaster();
            appraisalMaster.setEmployeeId(empid);
            appraisalMaster.setAppraisalIntitationId(appraisalConfig.getAppraisalConfigId());
            appraisalMaster.setPeriod(appraisalConfig.getPeriod());
            appraisalMaster.setCreatedBy(appraisalConfig.getCreatedBy());
            appraisalMaster.setCreatedDate(new Date());
            appraisalMaster.setActive(true);
            appraisalMaster.setStatus("Initialized");
            AppraisalMaster saved = appraisalMasterRepository.save(appraisalMaster);
            // Create a new AppraisalEmpHistory object for each employee
            AppraisalEmpHistory appraisalEmpHistory = new AppraisalEmpHistory();
            appraisalEmpHistory.setAppraisalMasId(saved.getId());
            appraisalEmpHistory.setEmpId(empid);
            appraisalEmpHistory.setDate(new Date());
            appraisalEmpHistory.setStatus("Initialized");
            appraisalEmpHistory.setCreatedBy(appraisalConfig.getCreatedBy());
            appraisalEmpHistoryService.createAppraisalEmpHistory(appraisalEmpHistory);
        }
//        control.send();
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
