package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Skills;
import com.sentrifugo.performanceManagement.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillsService {

    @Autowired
    SkillsRepository skillsRepository;

    public  List<Skills> getAllSkills() {
        return  skillsRepository.findAll();
    }

    public List<Skills> addSkills(List<Skills> skill) {
     return skillsRepository.saveAll(skill);

    }


    public  String deleteSkills(Integer id) {
        skillsRepository.deleteById(id);
        return "Deleted Successfully";
    }

}
