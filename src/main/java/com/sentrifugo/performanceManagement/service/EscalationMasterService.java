package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.EscalationMaster;
import com.sentrifugo.performanceManagement.repository.AppraisalMasterRepository;
import com.sentrifugo.performanceManagement.repository.EscalationMasterRepository;
import com.sentrifugo.performanceManagement.vo.EscalateListView;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EscalationMasterService {

    @Autowired
    EscalationMasterRepository escalationMasterRepository;

    @Autowired
    AppraisalMasterRepository appraisalMasterRepository;

    public EscalationMaster addEscalationMaster(EscalationMaster escalationMaster,Integer employeeId) {

        Integer appraisalMaster = escalationMasterRepository.findAppraisalIdByEmployeeId(employeeId);

        EscalationMaster escalationMaster1 = new EscalationMaster();
        escalationMaster1.setAppraisalMasterId(appraisalMaster);
        escalationMaster1.setReason(escalationMaster.getReason());
        escalationMaster1.setComments(escalationMaster.getComments());
        escalationMaster1.setInitiatedBy("Self");
        escalationMaster1.setCreatedBy(employeeId);
        escalationMaster1.setUpdatedBy(employeeId);
        escalationMaster1.setStatus(escalationMaster.getStatus());
        escalationMaster1.setL2ManagerComments(escalationMaster.getL2ManagerComments());

       return escalationMasterRepository.save(escalationMaster1);

    }

    public List<EscalationMaster> getEscalateMasterDetails() {
        return escalationMasterRepository.findAll();
    }


    public Map<String,Object> findEscalationInDetailViewbyId(Integer id){
        return escalationMasterRepository.findEscalationInDetailViewbyId(id);
    }


    public List<EscalateListView> getAllEscalationDetails() {
        List<Object[]> results = escalationMasterRepository.findAllDetails();
        List<EscalateListView> escalateListViews = new ArrayList<>();

        for (Object[] result : results) {
            EscalateListView escalateListView = new EscalateListView();
            escalateListView.setEmployeeId(String.valueOf((Long) result[1]));
            escalateListView.setDesignation((String) result[2]);
            escalateListView.setDepartment((String) result[3]);
            escalateListView.setEmployeeName((String) result[4]);
            escalateListView.setEscalationInitiatedBy((String) result[5]);
            escalateListView.setStatus((String) result[6]);
            escalateListViews.add(escalateListView);
        }

        return escalateListViews;
    }

}
