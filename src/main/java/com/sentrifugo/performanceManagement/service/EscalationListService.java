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
    public ResponseEntity<List<EscalationList>> getEmployeeBYStatus(String status) {
        return new ResponseEntity<>(repo.findBystatus(status), HttpStatus.OK);
    }

    public ResponseEntity<List<EscalationList>> getEmployeeBYDepart(String department) {
        return new ResponseEntity<>(repo.findBydepartment(department), HttpStatus.OK);
    }

    public ResponseEntity<List<EscalationList>> getEmployeeBYDesig(String designation) {
        return new ResponseEntity<>(repo.findBydesignation(designation), HttpStatus.OK);
    }

    public ResponseEntity<List<EscalationList>> getAll() {
        return new ResponseEntity<>(repo.findAll(),HttpStatus.OK);
    }
}
