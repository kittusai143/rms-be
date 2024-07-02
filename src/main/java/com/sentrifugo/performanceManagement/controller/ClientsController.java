package com.sentrifugo.performanceManagement.controller;

import com.sentrifugo.performanceManagement.entity.Clients;
import com.sentrifugo.performanceManagement.service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${custom.frontendUrl}")
@RequestMapping("clients")
public class ClientsController {

    @Autowired
    public ClientsService clientsService;

    @GetMapping("/getAll")
    public List<Map<String,?>> getAllClients(){
        return clientsService.getAllClients();
    }

    @PostMapping("/add")
    ResponseEntity<?> addclient(@RequestBody Clients client){
        Map<String,String> map=new HashMap<>();
        try {
            clientsService.addclient(client);
            map.put("message","added");
            return ResponseEntity.ok(map);
        }
        catch (Exception e) {
            map.put("error",e.toString());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    @PutMapping("/update")
    ResponseEntity<?> updateClient(@RequestBody Clients clients){
        Map<String,String> map=new HashMap<>();
        try {
            return ResponseEntity.ok(clientsService.updateclient(clients));
        }
        catch (Exception e){
            map.put("error",e.toString());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    @DeleteMapping("/delete")
    ResponseEntity<?> deleteClient(@RequestParam Long id){
        Map<String,String> map=new HashMap<>();
        try {
            if(clientsService.deleteclient(id))
            { map.put("message","deleted");
                return ResponseEntity.ok(map);
            }
            else {
                map.put("message","id-not-found");
                return ResponseEntity.ok(map);
            }
        }
        catch (Exception e){
            map.put("error",e.toString());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    @GetMapping("/getallCids")
    public List<String> getAllClientIds(){
        return clientsService.getAllClientIds();
    }


    @GetMapping("getClientNames")
    public List<Object> getAllClientNames() {
        return clientsService.getAllClientNames();
    }
    @GetMapping("getDistinctClientNames")
    public List<Object> getAllDistinctClientNames() {
        return clientsService.getAllDistinctClientNames();
    }


}
