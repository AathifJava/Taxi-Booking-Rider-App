package com.nova.aathif.taxibookingapi.controller;

import com.nova.aathif.taxibookingapi.dto.PaymentHistoryDTO;
import com.nova.aathif.taxibookingapi.dto.RequestMetaDTO;
import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import com.nova.aathif.taxibookingapi.model.Rider;
import com.nova.aathif.taxibookingapi.repository.CustomerTripRepository;
import com.nova.aathif.taxibookingapi.repository.RiderRepository;
import com.nova.aathif.taxibookingapi.service.PaymentScreenRiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentScreenRiderController {
    @Autowired
    PaymentScreenRiderService paymentScreenRiderService;

    @GetMapping("/getPaymentHistory/{app_id}")
    public ResponseEntity<?> getPaymentHistory(@PathVariable("app_id") String appId, @RequestBody PaymentHistoryDTO paymentHistoryDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return paymentScreenRiderService.paymentHistory(paymentHistoryDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

    @GetMapping("/getTodayAccountHisotry/{app_id}")
    public ResponseEntity<?> getTodayAccountHisotry(@PathVariable("app_id") String appId, @RequestBody VerificationCodeDTO verificationCodeDTO){
        if (appId.equals("novatechzone_customer_app")) {
            return paymentScreenRiderService.todayAccountHisotry(verificationCodeDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid App Id");
        }
    }

}
