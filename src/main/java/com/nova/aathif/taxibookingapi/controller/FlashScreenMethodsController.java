package com.nova.aathif.taxibookingapi.controller;

import com.nova.aathif.taxibookingapi.dto.UpdateRiderCurrentLocationDTO;
import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import com.nova.aathif.taxibookingapi.service.FlashScreenMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flash")
public class FlashScreenMethodsController {

    @Autowired
    FlashScreenMethodsService flashScreenMethodsService;

    @GetMapping("/checkRiderPendingTrip/{app_id}")
    public ResponseEntity<?> checkRiderPendingTrip(@PathVariable("app_id") String appId, @RequestBody VerificationCodeDTO verificationCodeDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return flashScreenMethodsService.checkPendingTrip(verificationCodeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @GetMapping("/getRiderInitialData/{app_id}")
    public ResponseEntity<?> getRiderInitialData(@PathVariable("app_id") String appId, @RequestBody VerificationCodeDTO verificationCodeDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return flashScreenMethodsService.getRiderData(verificationCodeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

}
