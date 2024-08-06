package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Users;
import com.sentrifugo.performanceManagement.repository.EmailAuthenticationRepo;
import com.sentrifugo.performanceManagement.repository.UsersRepository;
import com.sentrifugo.performanceManagement.service.UsersService;
import com.sentrifugo.performanceManagement.vo.UserResponse;
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
    @Autowired
    private UsersService usersService;

    @GetMapping("loginByPassword/{email}/{pwd}")
    public ResponseEntity<?> checkPassword(@PathVariable String email,@PathVariable String pwd){
        try{
        Map<String,Object> map=new HashMap<>();
        Users user= urepo.findByEmail(email);
//        System.out.println(user);
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
//    @GetMapping("/getdetails/{employeeid}")
//    public ResponseEntity<?> getdata(@PathVariable String employeeid) {
//        try {
//            Users user = urepo.findByEmployeeId(employeeid);
//            if (user != null) {
//                return ResponseEntity.ok(user);
//            }
//            return ResponseEntity.ok("No data for that employee");
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
//        }
//
//
//    }

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

    @GetMapping("getEmpIds")
    public List<Map<String, Object>> getEmployeeId() {
        return urepo.getEmployeeId();
    }
    @GetMapping("getEmpData/{empId}")
    public UserResponse getbyEmployeeID(@PathVariable("empId") String empid) {
        return usersService.getbyEmployeeID(empid);
    }
    @PostMapping("loginByPassword")
    public ResponseEntity<?> checkPassword(@RequestBody Map<String,String> u){
        try{
//            System.out.println("here");
            Map<String,Object> map=new HashMap<>();
            Users user= urepo.findByEmail(u.get("email"));
//            System.out.println(user);
            if(user!=null)
            {
//                System.out.println(user.getPassword()+" "+u.get("password"));
                if(user.getPassword().equals(u.get("password")))
                {
//                map.put("message","success");
                    Map<String,Object> var=customRepository.findDetailsBYEmail(u.get("email"));
                    // map.put("data",var);
//                    System.out.println(var);
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
//    @GetMapping("/getemployeestatus")
//    public ResponseEntity<?>  getEmployeesStatus(){
//        try{
//            System.out.println("HI");
//            List<Map<String,String>> users=repo.findDetailsWithStatus();
//            System.out.println("Users"+users);
//            int total=0;
//            int sub = 0;
//            int progress = 0;
//            int yet=0;
//            for (Map<String, String> obj : users) {
//                total++;
//                String status = obj.get("status");
//                if (status != null) {
//                    if ("EmployeeSubmitted".equals(status) || "ManagerSubmitted".equals(status) || "EmployeeEscalated".equals(status) || "HRSubmitted".equals(status) || "closed".equals(status)) {
//                        System.out.println("matched");
//                        sub++;
//                    } else if ("saved".equals(status)) {
//                        progress++;
//                    } else if ("Initialized".equals(status)) {
//                        yet++;
//                    }
//                }
//            }
//            Map<String,Integer> map=new HashMap<>();
//            map.put("Yet to start",yet);
//            map.put("In progress",progress);
//            map.put("Submitted",sub);
//            return ResponseEntity.ok(map);
//        }
//        catch (Exception e)
//        {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
//        }
//
//    }
//    @GetMapping("emplis")
//    //to get total Employee Submitted count
//    public ResponseEntity<?> find()
//    {
//        List<Map<String,Object>> vales=repo.find();
//
//        int submittedcount=0;
//        for(Map<String,Object> obj: vales)
//        {
//            Object s=obj.get("status");
//            if(s.equals("EmployeeSubmitted") || s.equals("ManagerSubmitted")||s.equals("HRSubmitted") || s.equals("EmployeeEscalated") || s.equals("closed"))
//                submittedcount++;
//        }
//        return ResponseEntity.ok(vales);
//
//    }

}