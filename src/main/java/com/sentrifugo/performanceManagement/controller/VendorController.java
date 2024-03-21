package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Vendor;
import com.sentrifugo.performanceManagement.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("vendor")
public class VendorController {
    @Autowired
    public VendorService vendorService;

    @GetMapping("/getAll")
    public List<Vendor> getAllVendor(){
        return vendorService.getAllVendor();
    }
}
