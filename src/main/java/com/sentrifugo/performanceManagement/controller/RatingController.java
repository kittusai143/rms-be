package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Rating;
import com.sentrifugo.performanceManagement.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
@CrossOrigin(origins = "http://localhost:3000")
public class RatingController {

    @Autowired
    private RatingService ratingService;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get")
    public List<Rating> getAllRating() {
        return ratingService.getAllRating();
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public List<Rating> addRating(@RequestBody List<Rating> ratings){
        return ratingService.addRating(ratings);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public String deleteRating(@PathVariable ("id")Integer id){
        return ratingService.deleteRating(id);


    }
}