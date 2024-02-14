package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Role;
import com.sentrifugo.performanceManagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

