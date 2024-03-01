package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Role;
import com.sentrifugo.performanceManagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "${custom.frontendUrl}")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/getroles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
}
