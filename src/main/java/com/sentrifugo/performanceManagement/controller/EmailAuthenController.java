package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.service.EmailAuthenService;
import com.sentrifugo.performanceManagement.vo.UserAndRoleDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("login")
public class EmailAuthenController {

    @Autowired
    private EmailAuthenService service;

    @GetMapping("verify")
    public ResponseEntity<?> getdetails(@RequestParam String email) {
        System.out.println(email);
        List<UserAndRoleDetailsDto> users = service.verify(email);
        if(users.isEmpty()) {
            String message = "Record not found for email: " + email;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

        } else {
            return ResponseEntity.ok(users); // Users found
        }
    }

}
