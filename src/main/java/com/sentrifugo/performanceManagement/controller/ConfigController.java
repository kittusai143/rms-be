package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Config;
import com.sentrifugo.performanceManagement.repository.ConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class ConfigController {

    @Autowired
    private ConfigRepo repo;

    @GetMapping("GetConfig")
   public ResponseEntity<?> getall() {
        try {
            List<Config> config = repo.findAll();
            if (config.isEmpty())
            {
                Map<String,String> map=new HashMap<>();
                map.put("status","No details found");
                List<Map<String,String>> lis=new ArrayList<>();
                return ResponseEntity.ok(lis);
            }
            else
                return ResponseEntity.ok(config);
        }
        catch (Exception e)
        {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


}
