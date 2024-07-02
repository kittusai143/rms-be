package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.ConsultantData;
import com.sentrifugo.performanceManagement.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consultants")
@CrossOrigin(origins = "${custom.frontendUrl}")
public class ConsultantController {

    @Autowired
    private ConsultantService consultantDataService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ConsultantData>> getAllConsultants() {
        List<ConsultantData> consultants = consultantDataService.getAllConsultants();
        return new ResponseEntity<>(consultants, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ConsultantData> addConsultant(@RequestBody ConsultantData consultantData) {
        ConsultantData newConsultant = consultantDataService.addConsultant(consultantData);
        return new ResponseEntity<>(newConsultant, HttpStatus.CREATED);
    }

    @GetMapping("/{consultantID}")
    public ResponseEntity<ConsultantData> getConsultantById(@PathVariable Integer consultantID) {
        Optional<ConsultantData> consultant = consultantDataService.getConsultantById(consultantID);
        return consultant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update")
    public ResponseEntity<ConsultantData> updateConsultant( @RequestBody ConsultantData consultantData) {
        ConsultantData updatedConsultant = consultantDataService.updateConsultant(consultantData);
        if (updatedConsultant != null) {
            return new ResponseEntity<>(updatedConsultant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
