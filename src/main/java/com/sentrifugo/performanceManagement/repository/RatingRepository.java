package com.sentrifugo.performanceManagement.repository;



import com.sentrifugo.performanceManagement.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating,Integer> {
}
