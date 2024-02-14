package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.AppraisalMaster;
import com.sentrifugo.performanceManagement.entity.EscalationMaster;
import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import com.sentrifugo.performanceManagement.repository.EscalationMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class EscalationMasterService {

    @Autowired
    EscalationMasterRepository escalationMasterRepository;

    @Autowired
    AppraisalMasterRepository appraisalMasterRepository;

    public EscalationMaster addEscalationMaster(EscalationMaster escalationMaster,Integer employeeId) {

        Integer appraisalMaster = appraisalMasterRepository.findAppraisalIdByEmployeeID(employeeId);

        EscalationMaster escalationMaster1 = new EscalationMaster();

        escalationMaster1.setAppraisal_master_id(appraisalMaster);
        escalationMaster1.setReason(escalationMaster.getReason());
        escalationMaster1.setComments(escalationMaster.getComments());
        escalationMaster1.setInitiated_by("Self");
        escalationMaster1.setCreated_by(employeeId);
        escalationMaster1.setUpdated_by(employeeId);
        escalationMaster1.setStatus(

                escalationMaster.getStatus());
        escalationMaster1.setL2_manager_comments(escalationMaster.getL2_manager_comments());

       return escalationMasterRepository.save(escalationMaster1);

    }

}
