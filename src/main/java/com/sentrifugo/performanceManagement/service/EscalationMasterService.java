//package com.sentrifugo.performanceManagement.service;
//
//import com.sentrifugo.performanceManagement.entity.EscalationMaster;
//import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
//import com.sentrifugo.performanceManagement.repository.EscalationMasterRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EscalationMasterService {
//
//    @Autowired
//    EscalationMasterRepository escalationMasterRepository;
//
//    @Autowired
//    AppraisalMasterRepository appraisalMasterRepository;
//
//    public EscalationMaster addEscalationMaster(EscalationMaster escalationMaster,Integer employeeId) {
//
//        Integer appraisalMaster = escalationMasterRepository.findAppraisalIdByEmployeeId(employeeId);
//
//        EscalationMaster escalationMaster1 = new EscalationMaster();
//        escalationMaster1.setAppraisalMasterId(appraisalMaster);
//        escalationMaster1.setReason(escalationMaster.getReason());
//        escalationMaster1.setComments(escalationMaster.getComments());
//        escalationMaster1.setInitiatedBy("Self");
//        escalationMaster1.setCreatedBy(employeeId);
//        escalationMaster1.setUpdatedBy(employeeId);
//        escalationMaster1.setStatus(escalationMaster.getStatus());
//        escalationMaster1.setL2ManagerComments(escalationMaster.getL2ManagerComments());
//
//       return escalationMasterRepository.save(escalationMaster1);
//
//    }
//
//    public List<EscalationMaster> getEscalateMasterDetails() {
//        return escalationMasterRepository.findAll();
//    }
//}
