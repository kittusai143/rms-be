package com.sentrifugo.performanceManagement.repository;

import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceAllocationRepository extends JpaRepository<ResourceAllocation,Long> {
    @Query("SELECT ra FROM ResourceAllocation ra WHERE ra.location IN :locations")
    List<ResourceAllocation> findByLocation(List<String> locations);
    @Query("SELECT ra FROM ResourceAllocation ra WHERE ra.skillset1 LIKE CONCAT('%', :skill, '%') OR ra.skillset2 LIKE CONCAT('%', :skill, '%')")
    List<ResourceAllocation> findBySkill(String skill);

    @Query("SELECT ra FROM ResourceAllocation ra WHERE ra.billability IN :billabilities")
    List<ResourceAllocation> findByBillability(List<String> billabilities);

    @Query("SELECT ra FROM ResourceAllocation ra WHERE ra.location IN :locations AND (ra.skillset1 LIKE CONCAT('%', :skill, '%') OR ra.skillset2 LIKE CONCAT('%', :skill, '%'))")
    List<ResourceAllocation> findByLocationAndSkill(List<String> locations, String skill);

    @Query("SELECT ra FROM ResourceAllocation ra WHERE ra.location IN :locations AND (ra.skillset1 IN :skills OR ra.skillset2 IN :skills)")
    List<ResourceAllocation> findByLocationAndSkills(List<String> locations, List<String> skills);

    @Query("SELECT ra FROM ResourceAllocation ra WHERE ra.location IN :locations AND ra.billability IN :billabilities")
    List<ResourceAllocation> findByLocationAndBillability(List<String> locations, List<String> billabilities);

    @Query("SELECT ra FROM ResourceAllocation ra WHERE (ra.skillset1 LIKE CONCAT('%', :skill, '%') OR ra.skillset2 LIKE CONCAT('%', :skill, '%')) AND ra.billability IN :billabilities")
    List<ResourceAllocation> findBySkillAndBillability(String skill, List<String> billabilities);

    @Query("SELECT ra FROM ResourceAllocation ra WHERE (ra.skillset1 IN :skills OR ra.skillset2 IN :skills) AND ra.billability IN :billabilities")
    List<ResourceAllocation> findBySkillsAndBillability(List<String> skills, List<String> billabilities);

    @Query("SELECT ra FROM ResourceAllocation ra WHERE ra.location IN :locations AND (ra.skillset1 LIKE CONCAT('%', :skill, '%') OR ra.skillset2 LIKE CONCAT('%', :skill, '%')) AND ra.billability IN :billabilities ")
    List<ResourceAllocation> findByLocationAndSkillAndBillability(List<String> locations, String skill, List<String> billabilities);

    @Query("SELECT ra FROM ResourceAllocation ra WHERE ra.location IN :locations AND (ra.skillset1 IN :skills OR ra.skillset2 IN :skills) AND ra.billability IN :billabilities ")
    List<ResourceAllocation> findByLocationAndSkillsAndBillability(List<String> locations, List<String> skills, List<String> billabilities);


}
