package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Parameter;
import com.sentrifugo.performanceManagement.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterService {

    @Autowired
    ParameterRepository parameterRepository;

    public List<Parameter> getAllParameters() {
        return  parameterRepository.findAll();
    }

    public List<Parameter> addParameters(List<Parameter> parameter) {

        System.out.println(parameter+" parameter");


        return parameterRepository.saveAll(parameter);

    }

    public String deleteParameters(Integer id) {
        parameterRepository.deleteById(id);
        return "Deleted Successfully";
    }

}
