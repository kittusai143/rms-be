//package com.sentrifugo.performanceManagement.service;
//
//import com.sentrifugo.performanceManagement.Exceptions.ResourceNotFoundException;
//import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
//import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
//import com.sentrifugo.performanceManagement.entity.Employees;
//import com.sentrifugo.performanceManagement.repository.AppraisalConfigRepository;
//import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class AppraisalConfigService {
//
//    @Autowired
//    private AppraisalConfigRepository appraisalConfigRepository;
//    @Autowired
//    private AppraisalMasterRepository appraisalMasterRepository;
//    @Autowired
//    private EmployeeService employeeService;
//    public List<AppraisalConfig> getAllAppraisalConfig() {
//        return appraisalConfigRepository.findAll();
//    }
//
//    public AppraisalConfig getAppraisalConfigById(Long appraisalConfigId) throws ResourceNotFoundException {
//        return appraisalConfigRepository.findById(appraisalConfigId)
//                .orElseThrow(() -> new ResourceNotFoundException("Appraisal Config data not found for this id " + appraisalConfigId));
//    }
//
//    public AppraisalConfig createAppraisalConfig(AppraisalConfig appraisalConfig) {
//        AppraisalConfig savedAppraisalConfig = appraisalConfigRepository.save(appraisalConfig);
//        createAppraisalMastersForEmployees(savedAppraisalConfig);
//        return savedAppraisalConfig;
//    }
//    private void createAppraisalMastersForEmployees(AppraisalConfig appraisalConfig) {
//        List<Employees> employees = employeeService.getEmployeesByBusinessUnitAndDepartment(
//                appraisalConfig.getBusinessUnit(), appraisalConfig.getDepartment());
//
//        Date currentDate = new Date();
//
//        for (Employees employee : employees) {
//            AppraisalMaster appraisalMaster = new AppraisalMaster();
//            appraisalMaster.setEmployeeId(employee.getId());
//            appraisalMaster.setAppraisalIntitationId(appraisalConfig.getAppraisalConfigId());
//            appraisalMaster.setPeriod(currentDate); // Set period to current date
//            appraisalMaster.setCreatedBy(employee.getId()); // Assuming createdBy is the employee ID
//            appraisalMaster.setCreatedDate(currentDate);
//            appraisalMaster.setActive(true); // Assuming it should be active by default
//            appraisalMaster.setStatus("Pending"); // Set default status
//
//            appraisalMasterRepository.save(appraisalMaster);
//        }
//    }
//    public AppraisalConfig updateAppraisalConfig(Long appraisalConfigId, AppraisalConfig appraisalConfigdetails) throws ResourceNotFoundException {
//        AppraisalConfig appraisalConfig = appraisalConfigRepository.findById(appraisalConfigId)
//                .orElseThrow(() -> new ResourceNotFoundException("Appraisal configs not found for this id :: " + appraisalConfigId));
//        // Update the fields
//        appraisalConfig.setAppraisalMode(appraisalConfigdetails.getAppraisalMode());
//        appraisalConfig.setWorkflowConfigId(appraisalConfigdetails.getWorkflowConfigId());
//        appraisalConfig.setDepartment(appraisalConfigdetails.getDepartment());
//        appraisalConfig.setEligibility(appraisalConfigdetails.getEligibility());
//        appraisalConfig.setBusinessUnit(appraisalConfigdetails.getBusinessUnit());
//        appraisalConfig.setEmployeeDueDate(appraisalConfigdetails.getEmployeeDueDate());
//        appraisalConfig.setEnableTo(appraisalConfigdetails.getEnableTo());
//        appraisalConfig.setManagerDueDate(appraisalConfigdetails.getManagerDueDate());
//        appraisalConfig.setFromYear(appraisalConfigdetails.getFromYear());
//        appraisalConfig.setParameter(appraisalConfigdetails.getParameter());
//        appraisalConfig.setRating(appraisalConfigdetails.getRating());
//        appraisalConfig.setPeriod(appraisalConfigdetails.getPeriod());
//        appraisalConfig.setProcessStatus(appraisalConfigdetails.getProcessStatus());
//        appraisalConfig.setToYear(appraisalConfigdetails.getToYear());
//        appraisalConfig.setStatus(appraisalConfigdetails.getStatus());
//
//        return appraisalConfigRepository.save(appraisalConfig);
//    }
//
//    public void deleteAppraisalConfig(Long appraisalConfigId) throws ResourceNotFoundException {
//        AppraisalConfig appraisalConfig = appraisalConfigRepository.findById(appraisalConfigId)
//                .orElseThrow(() -> new ResourceNotFoundException("Appraisal configs not found for this id :: " + appraisalConfigId));
//        appraisalConfigRepository.delete(appraisalConfig);
//    }
//
//}
