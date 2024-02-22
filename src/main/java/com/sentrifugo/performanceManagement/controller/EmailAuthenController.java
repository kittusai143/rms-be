package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.service.EmailAuthenService;
import com.sentrifugo.performanceManagement.vo.UserAndRoleDetailsDto;
import com.sentrifugo.performanceManagement.vo.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("login")
public class EmailAuthenController {

    @Autowired
    private EmailAuthenService service;

    @GetMapping("verify")
    public ResponseEntity<?> getdetails(@RequestParam String email) {
        System.out.println(email);
        Map<String,Object> valueList = service.verify(email);

        if (valueList.isEmpty()) {
            Map<String,String> map=new HashMap<>();
            map.put("status","Record not found for email: " + email);
            return ResponseEntity.ok(map);
        }

        return ResponseEntity.ok(valueList);
    }


    @GetMapping("getby")
    public ResponseEntity<?> getfrom(@RequestParam Integer id) {
         try {
            Map<String, Object> valuesList = service.getdetails(id);

            if (valuesList.isEmpty()) {
                Map<String, String> map = new HashMap<>();
                map.put("status", "No Record found with given id: " + id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
            }

            return ResponseEntity.ok(valuesList);
        } catch (Exception e) {
            // Handle exceptions here
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("status", "Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }


}
