
package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.service.TrainingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("training")
public class TrainingDetailsController {

    @Autowired
    private TrainingDetailsService trainingDetailsService;
    @GetMapping("getByTrainingName/{startDate}/{endDate}")
    public ResponseEntity<?> getByTrainingName(@PathVariable(name="startDate")String start,@PathVariable(name="endDate")String end){
        try{
            List<Map<String,Integer>> list= trainingDetailsService.getByTrainingName(start,end);
            if(list.isEmpty()){
                Map<String,String > map=new HashMap<>();
                map.put("message","Error fetching the data");
                List<Map<String,String>> l=new ArrayList<>();
                l.add(map);
                return ResponseEntity.ok(l);
            }
            else{
                return ResponseEntity.ok(list);
            }
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @GetMapping("getByTrainingArea/{startDate}/{endDate}")
    public ResponseEntity<?> getByTrainingArea(@PathVariable(name="startDate")String start,@PathVariable(name="endDate")String end){
        try{
            List<Map<String,Integer>> list= trainingDetailsService.getByTrainingArea(start,end);
            if(list.isEmpty()){
                Map<String,String > map=new HashMap<>();
                map.put("message","Error fetching the data");
                List<Map<String,String>> l=new ArrayList<>();
                l.add(map);
                return ResponseEntity.ok(l);
            }
            else{
                return ResponseEntity.ok(list);
            }
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
    @GetMapping("getTotalTrainingHrs/{startDate}/{endDate}")
    public ResponseEntity<?> getTotalTrainingHrs(@PathVariable(name="startDate")String start,@PathVariable(name="endDate")String end){
        try{
            List<Map<String,Integer>> list= trainingDetailsService.getTotalTrainingHrs(start,end);
            if(list.isEmpty()){
                Map<String,String > map=new HashMap<>();
                map.put("message","Error fetching the data");
                List<Map<String,String>> l=new ArrayList<>();
                l.add(map);
                return ResponseEntity.ok(l);
            }
            else{
                return ResponseEntity.ok(list);
            }
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/getQuarterlyData/{year}")
    public List<Map<String,Integer>> getQuarterlyData(@PathVariable ("year")String year){
        return trainingDetailsService.getQuarterlyData(year);
    }

    @GetMapping("/getQuarterlyNominatedData/{year}")
    public List<Map<String,Integer>> getQuarterlyNominatedData(@PathVariable ("year")String year){
        return trainingDetailsService.getQuarterlyNominatedData(year);
    }

    @GetMapping("/getQuarterlyTechAreaData/{year}")
    public List<Map<String,Integer>> getQuarterlyTechAreaData(@PathVariable ("year")String year){
        return trainingDetailsService.getQuarterlyTechAreaData(year);
    }

}
