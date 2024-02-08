package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.repository.EmailAuthenticationRepo;
import com.sentrifugo.performanceManagement.vo.UserAndRoleDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailAuthenService {

    @Autowired
    private EmailAuthenticationRepo Repo;


  public   List<String>  verify(String email)
  {
      return Repo.findDetailsBYEmail(email);
  }

}
