package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Rating;
import com.sentrifugo.performanceManagement.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    public List<Rating> addRating(List<Rating> ratings) {
        return ratingRepository.saveAll(ratings);

    }

    public String deleteRating(Integer id) {
        ratingRepository.deleteById(id);
        return "Deleted Successfully";
    }
}
