package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Parameter;
import com.sentrifugo.performanceManagement.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parameters")
@CrossOrigin(origins = "${custom.frontendUrl}")
public class ParameterController {

    @Autowired
    private ParameterService parameterService;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<Parameter> getAllParameters() {
        return parameterService.getAllParameters();
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public List<Parameter> addParameters(@RequestBody List<Parameter> parameter){
        return parameterService.addParameters(parameter);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public String deleteParameter(@PathVariable ("id")Integer id){
        return parameterService.deleteParameters(id);


    }
}
