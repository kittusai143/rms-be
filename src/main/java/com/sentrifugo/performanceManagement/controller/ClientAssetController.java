package com.sentrifugo.performanceManagement.controller;


import com.sentrifugo.performanceManagement.entity.ClientAsset;
import com.sentrifugo.performanceManagement.service.ClientAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("client-assets")
@CrossOrigin(origins = "${custom.frontendUrl}")
public class ClientAssetController {

    @Autowired
    private ClientAssetService assetService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAssets() {

            List<ClientAsset> assets = assetService.getAllAssets();
            return ResponseEntity.ok(assets);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAsset(@RequestBody ClientAsset asset) {
        Map<String,String> map=new HashMap<>();
        try {
            ClientAsset savedAsset = assetService.addAsset(asset);
            return ResponseEntity.ok(savedAsset);
        }
        catch (Exception e){
            map.put("error",e.toString());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAsset(@RequestBody ClientAsset updatedAsset) {
        Map<String, Object> response = new HashMap<>();
        try {
//            ClientAsset existingAsset = assetService.getAssetById(updatedAsset.getId());
//            if (existingAsset == null) {
//                response.put("error", "Asset not found");
//                return ResponseEntity.ok(response);
//            }
            ClientAsset savedAsset = assetService.updateAsset(updatedAsset);
            response.put("updated-data", savedAsset);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
