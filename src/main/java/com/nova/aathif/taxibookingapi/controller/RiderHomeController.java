package com.nova.aathif.taxibookingapi.controller;

import com.nova.aathif.taxibookingapi.dto.*;
import com.nova.aathif.taxibookingapi.service.RiderHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class RiderHomeController {
    @Autowired
    RiderHomeService riderHomeService;

    @PostMapping("/riderAcceptTrip/{app_id}")
    public ResponseEntity<?> riderAcceptTrip(@PathVariable("app_id") String appId, @RequestBody RiderAcceptTripDTO riderAcceptTripDTO) {
        if (appId.equals("novatechzone_customer_app")) {
            return riderHomeService.riderAcceptTrip(riderAcceptTripDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/riderArrived/{trip_id}/{app_id}")
    public ResponseEntity<?> riderArrived(@PathVariable("app_id") String appId, @PathVariable("trip_id") int tripId, @RequestBody VerificationCodeDTO verificationCodeDTO) {
        if (appId.equals("novatechzone_customer_app")) {
            return riderHomeService.riderArrived(tripId, verificationCodeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/riderStartTrip/{trip_id}/{app_id}")
    public ResponseEntity<?> riderStartTrip(@PathVariable("app_id") String appId, @PathVariable("trip_id") int tripId, @RequestBody RiderStartTripDTO riderStartTripDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return riderHomeService.riderStartTrip(tripId, riderStartTripDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/riderEndTrip/{trip_id}/{app_id}")
    public ResponseEntity<?> riderEndTrip(@PathVariable("app_id") String appId, @PathVariable("trip_id") int tripId, @RequestBody RiderEndTripDTO riderEndTripDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return riderHomeService.riderEndTrip(tripId, riderEndTripDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/riderRejectTrip/{trip_id}/{app_id}")
    public ResponseEntity<?> riderRejectTrip(@PathVariable("app_id") String appId, @PathVariable("trip_id") int tripId, @RequestBody RiderRejectTripDTO riderRejectTripDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return riderHomeService.riderRejectTrip(tripId, riderRejectTripDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @GetMapping("/tripData/{trip_id}/{app_id}")
    public ResponseEntity<?> getTripDatariderById(@PathVariable("app_id") String appId, @PathVariable("trip_id") int tripId, @RequestBody TripDataDTO tripDataDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return riderHomeService.getTripDatariderById(tripId, tripDataDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @PostMapping("/switchDriverMode/{trip_id}/{app_id}")
    public ResponseEntity<?> switchDriverMode(@PathVariable("app_id") String appId, @PathVariable("trip_id") int tripId, @RequestBody SwitchDriverModeDTO switchDriverModeDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return riderHomeService.switchDriverMode(tripId, switchDriverModeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

}
