package com.nova.aathif.taxibookingapi.controller;

import com.nova.aathif.taxibookingapi.dto.*;
import com.nova.aathif.taxibookingapi.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rider")
public class RiderController {

    @Autowired
    RiderService riderService;

    @PostMapping("/auth/register/{app_id}")
    public ResponseEntity<?> register(@PathVariable("app_id") String appId, @RequestBody RiderRegisterDTO riderRegisterDTO) {
        if (appId.equals("novatechzone_customer_app")) {
            return riderService.registerRider(riderRegisterDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/auth/register/otp/{app_id}")
    public ResponseEntity<?> otpRider(@PathVariable("app_id") String appId, @RequestBody RiderOtpDTO riderOtpDTO) {
        if (appId.equals("novatechzone_customer_app")) {
            return riderService.getVerificanCode(riderOtpDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/auth/verify/{app_id}")
    public ResponseEntity<?> verifyRiderMobile(@PathVariable("app_id") String appId, @RequestBody VerifyOtpDTO verifyOtpDTO) {
        if (appId.equals("novatechzone_customer_app")) {
            return riderService.verifiRiderMobile(verifyOtpDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }

    }

    @GetMapping("/auth/vehicleTypes/{app_id}")
    public ResponseEntity<?> vehicleTypes(@PathVariable("app_id") String appId) {
        if (appId.equals("novatechzone_customer_app")) {
            return riderService.vehicleTypes();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }

    }

    @PostMapping("/auth/paaswordResetRequest/{app_id}")
    public ResponseEntity<?> passwordResetRequest(@PathVariable("app_id") String appID, @RequestBody RiderOtpDTO riderOtpDTO) {
        if (appID.equals("novatechzone_customer_app")) {
            return riderService.getVerificanCode(riderOtpDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/auth/resetPassword/{app_id}")
    public ResponseEntity<?> resetPassword(@PathVariable("app_id") String appID, @RequestBody ResetPasswordDTO resetPasswordDTO){
        if (appID.equals("novatechzone_customer_app")) {
            return riderService.resetPassword(resetPasswordDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/auth/login/{app_id}")
    public ResponseEntity<?> login(@PathVariable("app_id") String appId, @RequestBody RiderLoginDTO riderLoginDTO) {
        if (appId.equals("novatechzone_customer_app")) {
            return riderService.login(riderLoginDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

}
