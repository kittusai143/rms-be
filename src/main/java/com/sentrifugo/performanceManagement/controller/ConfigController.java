package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Config;
import com.sentrifugo.performanceManagement.repository.ConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")

public class ConfigController {

    @Autowired
    private ConfigRepo repo;

    @GetMapping("GetConfig")
   public ResponseEntity<?> getall()
    {
        String message="No details found";
      List<Config> config=repo.findAll();
        if(config.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        else
            return  ResponseEntity.ok(config);
    }


}
