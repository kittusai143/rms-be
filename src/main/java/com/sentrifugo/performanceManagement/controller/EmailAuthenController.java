package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.service.EmailAuthenService;
import com.sentrifugo.performanceManagement.vo.UserAndRoleDetailsDto;
import com.sentrifugo.performanceManagement.vo.UserDetailsDto;
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
        List<String> valueList = service.verify(email);

        if (valueList.isEmpty()) {
            String message = "Record not found for email: " + email;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        String var = valueList.get(0);
        String[] parts = var.split(",");
        if (parts.length < 7) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data format");
        }
        UserAndRoleDetailsDto user = new UserAndRoleDetailsDto();
        user.setId(Integer.parseInt(parts[0]));
        user.setEmpRole(Integer.parseInt(parts[1]));
        user.setEmpRoleName(parts[2]);
        user.setEmpRoleType(parts[3]);
        user.setName(parts[4]);
        user.setEmail(parts[5]);
        user.setEmployeeId(parts[6]);
//        SELECT  u.id, u.empRole, r.rolename AS empRoleName, r.roletype AS empRoleType, u.name, u.email,  u.employeeId "

        return ResponseEntity.ok(user);
    }


    @GetMapping("getby")
    public ResponseEntity<?> getfrom(@RequestParam Integer id)
    {
        List<String> valuesList=service.getdetails(id);
        if(valuesList.isEmpty())
        {
            String message="Id not found";
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        String  var=valuesList.get(0);
        String[] parts = var.split(",");
        if (parts.length < 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data format");
        }
//        SELECT u.id, u.name,u.employeeId, e.bussinessunit, e.department " +
        UserDetailsDto user=new UserDetailsDto();
        user.setId(Integer.parseInt((parts[0])));
        user.setName(parts[1]);
        user.setEmployeeId(parts[2]);
        user.setBussinessunit(parts[3]);
        user.setDepartment(parts[4]);

         return ResponseEntity.ok(user);
    }


}
