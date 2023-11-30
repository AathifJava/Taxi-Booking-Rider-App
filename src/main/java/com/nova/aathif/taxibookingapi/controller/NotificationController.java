package com.nova.aathif.taxibookingapi.controller;

import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import com.nova.aathif.taxibookingapi.service.RiderNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    RiderNotificationService riderNotificationService;

    @GetMapping("/riderNotification/{app_id}")
    public ResponseEntity<?> checkRiderPendingTrip(@PathVariable("app_id") String appId, @RequestBody VerificationCodeDTO verificationCodeDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return riderNotificationService.riderNotification(verificationCodeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

}
