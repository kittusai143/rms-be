package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Users;
import com.sentrifugo.performanceManagement.repository.EmailAuthenticationRepo;
import com.sentrifugo.performanceManagement.repository.UsersRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.ObjectName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
public class UserController {

    @Autowired
    private EmailAuthenticationRepo customRepository;

    @Autowired
    private UsersRepository urepo;

    @GetMapping("/managers")
    public ResponseEntity<?> getManagers(@RequestParam Integer Id) {
         try {
             // Retrieve reporting manager name
             List<String> reportingManagerNames = customRepository.getReportingManagerNames(Id);

             if (reportingManagerNames.isEmpty())
                 return ResponseEntity.ok("No Manager Found");
             else
                 return ResponseEntity.ok(reportingManagerNames);
         }
         catch (Exception e)
         {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
         }

    }
    @GetMapping("/l2managers")
    public ResponseEntity<?> getL2Managers(@RequestParam Integer Id) {
        try {
            // Retrieve reporting manager name
            List<String> reportingManagerNames = customRepository.getL2ManagerNames(Id);

            if (reportingManagerNames.isEmpty())
                return ResponseEntity.ok("No Manager Found");
            else
                return ResponseEntity.ok(reportingManagerNames);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @Autowired
    private UsersRepository repo;



    public List<Map<String, Object>> getdetailsstatus(@RequestParam  Integer id)
    {
        List<Map<String, Object>> values = repo.findDetailsWithStatusByManagerId(id);
        if (values.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("Status", "No EMployee found for these managers");
            List<Map<String, Object>> lis = new ArrayList<>();
            lis.add(map);
            return lis;
        }
        return values;

    }

    @GetMapping("getManagers")
    //Managers With status
    public ResponseEntity<?> getallmanagers()
    {
        try {
            List<Map<String, Object>> values = repo.findManagers();
            if (values.isEmpty()) {
                Map<String, String> map = new HashMap<>();
                map.put("Status", "No  managers");
                List<Map<String, String>> lis = new ArrayList<>();
                lis.add(map);
                return ResponseEntity.ok(lis);
            }
            Map<String, String> map = new HashMap<>();
            for (Map<String, Object> obj : values) {
                Object id = obj.get("Id");


                String s = getstatus((Integer) id);

                map.put((String) obj.get("name"), s);
            }
            int total = 0;
            for (String status : map.values()) {
                if (status == "ReviewedAll") {
                    total++;
                }
            }
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("map", map);
            responseMap.put("total", total);

            return ResponseEntity.ok(responseMap);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }

    }

//  @GetMapping("/getemployestatus")
    public String getstatus(Integer id)
    {

      List<Map<String,Object>> values = getdetailsstatus(id);
        int Mcount = 0;
        int Ecount = 0;
        int total=0;
        for (Map<String, Object> obj : values) {
            // Access the 'status' field value
            total++;
            Object status = obj.get("status");
//            System.out.println(status);
            if ("ManagerSubmitted".equals(status) || "EmployeeEscalated".equals(status) || "HRSubmitted".equals(status) || "closed".equals(status)) {
                System.out.println("matched");
                Mcount++;
            } else {
                Ecount++;
            }
        }
        System.out.println(Mcount+""+total);
        if (Mcount == total) {
            return "ReviewedAll";
        } else if(Mcount == 0) {
            return "Yet to submit";
        } else {
            return "In Progress";
        }
    }
    @GetMapping("emplis")

    public ResponseEntity<?> find()
    {
        List<Map<String,Object>> vales=repo.find();

        int submittedcount=0;
        for(Map<String,Object> obj: vales)
        {
            Object s=obj.get("status");
            if(s.equals("EmployeeSubmitted") || s.equals("ManagerSubmitted")||s.equals("HRSubmitted") || s.equals("EmployeeEscalated") || s.equals("closed"))
                submittedcount++;
        }
        return ResponseEntity.ok(vales);

    }

    @GetMapping("loginByPassword/{sil_id}/{pwd}")
    public ResponseEntity<?> checkPassword(@PathVariable String sil_id,@PathVariable String pwd){
        Map<String,String> map=new HashMap<>();
        Users user= urepo.findByemployeeId(sil_id);
        System.out.println(user);
        if(user!=null)
        {
            if(user.getPassword().equals(pwd))
            {
                map.put("message","success");
                return ResponseEntity.ok(map);
            }
            else {
                map.put("message","incorrect-password");
                return ResponseEntity.ok(map);
            }
        }
        else {
            map.put("message","Invalid sil-Id");
            return ResponseEntity.ok(map);

        }

    }



}
