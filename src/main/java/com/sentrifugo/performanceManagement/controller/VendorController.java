

package com.sentrifugo.performanceManagement.controller;
import com.sentrifugo.performanceManagement.entity.Vendor;
import com.sentrifugo.performanceManagement.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("vendors")
public class VendorController {
    @Autowired
    public VendorService vendorService;

    @GetMapping("/getAll")
    public List<Vendor> getAllVendor(){

        return vendorService.getAllVendor();
    }


    @PostMapping("/add")
    public ResponseEntity<?> addVendor(@RequestBody Vendor vendor) {
        Map<String,String> map=new HashMap<>();
        try {
            Vendor v=vendorService.addVendor(vendor);
            return ResponseEntity.ok(v);
        } catch (Exception e) {
            System.out.println(vendor.getId());
            map.put("error",e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateVendor(@RequestBody Vendor updatedVendor) {
        Map<String,String > map = new HashMap<>();
        try {
            if(vendorService.updateVendor(updatedVendor).equals("updated")) {
                return ResponseEntity.ok(updatedVendor);
            } else  {
                map.put("message","no-record-found");
                return ResponseEntity.ok(map);
            }
        } catch (Exception e) {
            map.put("error",e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }



    @GetMapping("/getallVnames")
    public List<String> getallVendorNames(){
        return  vendorService.getAllVendorNames();
    }

//    @DeleteMapping("/deleteVendor/{id}")
//    public ResponseEntity<?> deleteVendor(@PathVariable("id") Long id) {
//        Map<String,String> map=new HashMap<>();
//        try {
//            Vendor existingVendor = vendorService.getVendorById(id);
//            if (existingVendor != null) {
//                vendorService.deleteVendor(id);
//                return ResponseEntity.ok("Vendor deleted successfully");
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            map.put("error",e.toString());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
//        }
//    }
@GetMapping("/getallVids")
public List<Map<String, Object>> getallVendorIds(){
    return  vendorService.getAllVendorIds();
}
}
