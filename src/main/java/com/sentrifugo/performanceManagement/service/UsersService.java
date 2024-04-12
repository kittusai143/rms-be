package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Users;
import com.sentrifugo.performanceManagement.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getUsersByRoleId(Integer roleId) {
        return usersRepository.findByEmpRole(roleId);
    }

}
