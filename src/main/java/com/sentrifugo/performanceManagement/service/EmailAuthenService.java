package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.repository.EmailAuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmailAuthenService {

    @Autowired
    private EmailAuthenticationRepo Repo;


  public Map<String,Object> verify(String email)
  {

      return Repo.findDetailsBYEmail(email);
  }

  public Map<String,Object> getdetails(Integer id)
  {
      return  Repo.findDetailsById(id);
  }


}
