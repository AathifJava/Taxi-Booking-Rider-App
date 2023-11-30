package com.nova.aathif.taxibookingapi.service;

import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface FlashScreenMethodsService {
    ResponseEntity<?> checkPendingTrip(VerificationCodeDTO verificationCodeDTO);

    ResponseEntity<?> getRiderData(VerificationCodeDTO verificationCodeDTO);
}
