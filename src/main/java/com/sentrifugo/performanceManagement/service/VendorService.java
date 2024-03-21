package com.sentrifugo.performanceManagement.service;

import com.sentrifugo.performanceManagement.entity.Vendor;
import com.sentrifugo.performanceManagement.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {
    @Autowired
    public VendorRepository vendorRepository;
    public List<Vendor> getAllVendor() {
        return vendorRepository.findAll();
    }
}
