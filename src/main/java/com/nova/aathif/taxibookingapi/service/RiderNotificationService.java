package com.nova.aathif.taxibookingapi.service;

import com.nova.aathif.taxibookingapi.dto.RiderNotificationDTO;
import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import com.nova.aathif.taxibookingapi.model.RiderNotification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RiderNotificationService {
    public void saveNotification(RiderNotificationDTO riderNotificationDTO);

    ResponseEntity<?> riderNotification(VerificationCodeDTO verificationCodeDTO);
}
