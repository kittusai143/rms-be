

package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.SowMaster;
import com.sentrifugo.performanceManagement.service.SowMasterService;
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
@RequestMapping("sow")
public class SowMasterController {
    @Autowired
    private SowMasterService sowMasterService;

    @GetMapping("getAll")
    public ResponseEntity<?>getAll(){
        try{
            List<SowMaster> result=sowMasterService.getAll();
            if(result.isEmpty()){
                List<Map<String,String>> list=new ArrayList<>();
                Map<String,String> map=new HashMap<>();
                map.put("message","No Sow data found");
                list.add(map);
                return ResponseEntity.ok(list);

            }
            else{
                return ResponseEntity.ok(result);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody SowMaster sowMaster){
        try{
            SowMaster sow=sowMasterService.add(sowMaster);
            if(sow==null){
                Map<String,String> map=new HashMap<>();
                map.put("message","Could not add the given sow details");
                return ResponseEntity.ok(map);
            }
            else{
                return ResponseEntity.ok(sow);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @PutMapping("/update")
    public  ResponseEntity<?> update(@RequestBody SowMaster updatedSow){
        try{
            SowMaster result=sowMasterService.update(updatedSow);
            if(result==null){
                Map<String,String > map=new HashMap<>();
                map.put("message","Error updating the sow data");
                return ResponseEntity.ok(map);
            }
            else {
                return ResponseEntity.ok(result);
            }
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @GetMapping("getAllSowIds")
    public List<String>getSowIds() {
        return sowMasterService.getSowIds();
    }
    @GetMapping("getSowData/{id}")
    public SowMaster getSowDataById(@PathVariable("id") String id) {
        return sowMasterService.getSowDataById(id);
    }

}
