package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Users;
import com.sentrifugo.performanceManagement.repository.EmailAuthenticationRepo;
import com.sentrifugo.performanceManagement.repository.UsersRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.ObjectName;
import java.util.*;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
public class UserController {

    @Autowired
    private EmailAuthenticationRepo customRepository;

    @Autowired
    private UsersRepository urepo;

    @GetMapping("loginByPassword/{email}/{pwd}")
    public ResponseEntity<?> checkPassword(@PathVariable String email,@PathVariable String pwd){
        try{
        Map<String,Object> map=new HashMap<>();
        Users user= urepo.findByEmail(email);
        System.out.println(user);
        if(user!=null)
        {
            if(user.getPassword().equals(pwd))
            {
//                map.put("message","success");
                Map<String,Object> var=customRepository.findDetailsBYEmail(email);
                // map.put("data",var);
                return ResponseEntity.ok(var);
            }
            else {
                map.put("message","incorrect-password");
                return ResponseEntity.ok(map);
            }
        }
        else {
            map.put("message","Invalid  email");
            return ResponseEntity.ok(map);

        }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }

    }
    @GetMapping("/getdetails/{employeeid}")
    public ResponseEntity<?> getdata(@PathVariable String employeeid) {
        try {
            Users user = urepo.findByEmployeeId(employeeid);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.ok("No data for that employee");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }


    }

    @GetMapping("/getManagers")
    public ResponseEntity<?> getEmail() {
        Date date=new Date();
        return ResponseEntity.ok(urepo.findActiveUsersByBirthday(date));
    }

    @GetMapping("/getBothEmails")
    public ResponseEntity<?> getAnniversary() {
        Date date=new Date();
        return ResponseEntity.ok(urepo.findEmployeesWithAnniversaryByDate(date));
    }



}