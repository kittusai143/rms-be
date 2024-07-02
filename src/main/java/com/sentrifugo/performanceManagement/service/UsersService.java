package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Users;
import com.sentrifugo.performanceManagement.repository.UsersRepository;
import com.sentrifugo.performanceManagement.vo.UserResponse;
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
    public UserResponse getbyEmployeeID(String employeeID){
        List<Users> usersList = usersRepository.findByEmployeeId(employeeID);
       UserResponse userResponse = new UserResponse();


        usersList.forEach(eachEmployee->{
            String roleName = usersRepository.findRoleNameByroleId(eachEmployee.getEmpRole());
            userResponse.setRoleName(roleName);
            userResponse.setEmployeeId(eachEmployee.getEmployeeId());
            userResponse.setDob(eachEmployee.getDob());
            userResponse.setEmail(eachEmployee.getEmail());
            userResponse.setName(eachEmployee.getName());
            userResponse.setUserFullName(eachEmployee.getUserFullName());
            userResponse.setContactNumber(eachEmployee.getContactNumber());

        });
        return userResponse;

    }
}
