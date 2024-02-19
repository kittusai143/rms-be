package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    List<Users> findByEmpRole(Integer empRole);

}
