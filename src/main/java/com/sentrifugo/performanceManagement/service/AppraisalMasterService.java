//package com.sentrifugo.performanceManagement.service;
//
//import com.sentrifugo.performanceManagement.entity.AppraisalConfig;
//import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
//import com.sentrifugo.performanceManagement.entity.Employees;
//import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AppraisalMasterService {
//    @Autowired
//    AppraisalMasterRepository appraisalMasterRepository;
//    @Autowired
//    private EmployeeService employeeService;
//    @Autowired
//    private AppraisalConfigService appraisalConfigService;
//
//    public List<AppraisalMaster> getAllAppraisalMaster() {
//        return appraisalMasterRepository.findAll();
//    }
//}