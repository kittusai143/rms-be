package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.entity.EscalationList;
import com.sentrifugo.performanceManagement.repository.EscalationListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class EscalationListService {
    @Autowired
    private EscalationListRepository repo;
    public List<EscalationList> getEmployeeBYStatus(String status) {
        return repo.findBystatus(status);
    }

    public List<EscalationList> getEmployeeBYDepart(String department) {
        return repo.findBydepartment(department);
    }

    public List<EscalationList> getEmployeeBYDesig(String designation) {
        return repo.findBydesignation(designation);
    }

    public List<EscalationList> getAll() {
        return repo.findAll();
    }
}
