package com.nova.aathif.taxibookingapi.controller;

import com.nova.aathif.taxibookingapi.dto.ImageDTO;
import com.nova.aathif.taxibookingapi.dto.UpdateDriverVehicleDetailsDTO;
import com.nova.aathif.taxibookingapi.dto.UpdateProfileDriverDTO;
import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import com.nova.aathif.taxibookingapi.service.UpdateProfileDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/riderProfile")
public class RiderProfileController {

    @Autowired
    UpdateProfileDriver updateProfileDriver;

    @PostMapping("/updateProfileDriver/{app_id}")
    public ResponseEntity<?> updateProfileDriver(@PathVariable("app_id") String appId, @RequestBody UpdateProfileDriverDTO updateProfileDriverDTO) {
        if (appId.equals("novatechzone_customer_app")) {
            return updateProfileDriver.updateProfile(updateProfileDriverDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/updateDriverVehicleDetails/{app_id}")
    public ResponseEntity<?> updateDriverVehicleDetails(@PathVariable("app_id") String appId, @RequestBody UpdateDriverVehicleDetailsDTO updateDriverVehicleDetailsDTO) {
        if (appId.equals("novatechzone_customer_app")) {
            return updateProfileDriver.updateDriverVehicle(updateDriverVehicleDetailsDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @GetMapping("/loadDriverProfileDetails/{app_id}")
    public ResponseEntity<?> loadDriverProfileDetails(@PathVariable("app_id") String appId, @RequestBody VerificationCodeDTO verificationCodeDTO) {
        if (appId.equals("novatechzone_customer_app")) {
            return updateProfileDriver.loadDriverProfile(verificationCodeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @GetMapping("/getDriverVehicleDetails/{app_id}")
    public ResponseEntity<?> getDriverVehicleDetails(@PathVariable("app_id") String appId, @RequestBody VerificationCodeDTO verificationCodeDTO) {
        if (appId.equals("novatechzone_customer_app")) {
            return updateProfileDriver.getDriverVehicle(verificationCodeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/updateRiderProfile/{app_id}")
    public ResponseEntity<?> updateProfileImage(@PathVariable("app_id") String appId, @RequestParam String verification, @RequestParam("imagePath") MultipartFile file) throws IOException {
        if (appId.equals("novatechzone_customer_app")) {
            return updateProfileDriver.profileImage(verification, file);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/updateDriverLisenceImage1/{app_id}")
    public ResponseEntity<?> updateDriverLisenceImage1(@PathVariable("app_id") String appId, @RequestParam String verification, @RequestParam("imagePath") MultipartFile file) throws IOException {
        if (appId.equals("novatechzone_customer_app")) {
            return updateProfileDriver.driverLisenceImage1(verification, file);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/updateDriverLisenceImage2/{app_id}")
    public ResponseEntity<?> updateDriverLisenceImage2(@PathVariable("app_id") String appId, @RequestParam String verification, @RequestParam("imagePath") MultipartFile file) throws IOException {
        if (appId.equals("novatechzone_customer_app")) {
            return updateProfileDriver.driverLisenceImage2(verification, file);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/updateDriverVehicleImage1/{app_id}")
    public ResponseEntity<?> updateDriverVehicleImage1(@PathVariable("app_id") String appId, @RequestParam String verification, @RequestParam("imagePath") MultipartFile file) throws IOException {
        if (appId.equals("novatechzone_customer_app")) {
            return updateProfileDriver.driverVehicleImage1(verification, file);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/updateDriverVehicleImage2/{app_id}")
    public ResponseEntity<?> updateDriverVehicleImage2(@PathVariable("app_id") String appId, @RequestParam String verification, @RequestParam("imagePath") MultipartFile file) throws IOException {
        if (appId.equals("novatechzone_customer_app")) {
            return updateProfileDriver.driverVehicleImage2(verification, file);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }



}
