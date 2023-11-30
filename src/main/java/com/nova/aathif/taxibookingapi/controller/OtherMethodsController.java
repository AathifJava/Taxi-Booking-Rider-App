package com.nova.aathif.taxibookingapi.controller;

import com.nova.aathif.taxibookingapi.dto.SendMessageRiderDTO;
import com.nova.aathif.taxibookingapi.dto.TripHistoryByRiderDTO;
import com.nova.aathif.taxibookingapi.dto.UpdateRiderCurrentLocationDTO;
import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import com.nova.aathif.taxibookingapi.service.OtherMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otherMethods")
public class OtherMethodsController {

    @Autowired
    OtherMethodsService otherMethodsService;

    @GetMapping("/getTripHistoryByRider/{app_id}")
    public ResponseEntity<?> getTripHistoryByRider(@PathVariable("app_id") String appId, @RequestBody TripHistoryByRiderDTO tripHistoryByRiderDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return otherMethodsService.getTripHistory(tripHistoryByRiderDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/sendMessageRider/{app_id}")
    public ResponseEntity<?> sendMessageRider(@PathVariable("app_id") String appId, @RequestBody SendMessageRiderDTO sendMessageRiderDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return otherMethodsService.sendMessage(sendMessageRiderDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/updateRiderCurrentLocation/{app_id}")
    public ResponseEntity<?> updateRiderCurrentLocation(@PathVariable("app_id") String appId, @RequestBody UpdateRiderCurrentLocationDTO updateRiderCurrentLocationDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return otherMethodsService.updateRiderLocation(updateRiderCurrentLocationDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

}
