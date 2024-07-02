

package com.sentrifugo.performanceManagement.service;
import com.sentrifugo.performanceManagement.entity.Vendor;
import com.sentrifugo.performanceManagement.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VendorService {
    @Autowired
    public VendorRepository vendorRepository;
    public List<Vendor> getAllVendor() {
        return vendorRepository.findAll();
    }

    public Vendor addVendor(Vendor vendor) {

        return  vendorRepository.save(vendor);
    }

    public String updateVendor(Vendor updatedVendor) {
        Optional<Vendor> existingVendor = vendorRepository.findById(updatedVendor.getId());
        if (existingVendor.isPresent()) {
            vendorRepository.save(updatedVendor);
            return "updated";
        }
        else
            return "no-record-found";
    }
    public List<Map<String, Object>> getAllVendorIds() {
        return  vendorRepository.findAllVendorId();
    }
//    public List<String> getAllVendorIds() {
//        return  vendorRepository.findAllVendorId();
//    }

    public List<String> getAllVendorNames() {
        return vendorRepository.findAllVendorNames();
    }
}
