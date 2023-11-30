package com.nova.aathif.taxibookingapi.service;

import com.nova.aathif.taxibookingapi.dto.PaymentHistoryDTO;
import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PaymentScreenRiderService {
    ResponseEntity<?> paymentHistory(PaymentHistoryDTO paymentHistoryDTO);

    ResponseEntity<?> todayAccountHisotry(VerificationCodeDTO verificationCodeDTO);
}
