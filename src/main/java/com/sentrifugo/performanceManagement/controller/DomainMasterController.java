package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.DomainMaster;
import com.sentrifugo.performanceManagement.repository.DomainMasterRepository;
import com.sentrifugo.performanceManagement.service.DomainMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("domain")
public class DomainMasterController {
    @Autowired
    private DomainMasterService domainMasterService;
    @Autowired
    private DomainMasterRepository domainMasterRepository;
    @GetMapping("getAll")
    public List<DomainMaster> getAll(){
        return domainMasterRepository.findAll();
    }
    @GetMapping("/getBy/{id}")
    public DomainMaster getById(@PathVariable Integer id){
        return domainMasterRepository.findById(id).orElse(null);
    }

    @GetMapping("getsubdomains")
    public ResponseEntity<?> getSUbdomains(@RequestParam(name = "domain") String domain){
        try{
            List<String> list=domainMasterRepository.findByDomainName(domain);
            if(list.isEmpty()){
                List<Map<String,String>>lis=new ArrayList<>();
                Map<String,String> map=new HashMap<>();
                map.put("message","No data found");
                lis.add(map);
                return ResponseEntity.ok(lis);
            }
            else{
                return ResponseEntity.ok(list);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);

        }
    }
    @GetMapping("distinctDomains")
    public  ResponseEntity<?> getDistinct(){
        try{
            List<String> list=domainMasterRepository.getAllDomainNames();
            if(list.isEmpty()){
                List<Map<String,String>>lis=new ArrayList<>();
                Map<String,String> map=new HashMap<>();
                map.put("message","No data found");
                lis.add(map);
                return ResponseEntity.ok(lis);
            }
            else{
                return ResponseEntity.ok(list);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);

        }
    }
    @PostMapping("addDomain")
    public String addDomain(@RequestBody DomainMaster domainMaster) {
        return domainMasterService.addDomain(domainMaster);
    }
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody DomainMaster domainMaster) {
        return ResponseEntity.ok(domainMasterService.update(domainMaster));
    }
}
