package com.nova.aathif.taxibookingapi.service.serviceImpl;

import com.nova.aathif.taxibookingapi.dto.RequestMetaDTO;
import com.nova.aathif.taxibookingapi.dto.RiderNotificationDTO;
import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import com.nova.aathif.taxibookingapi.model.RiderNotification;
import com.nova.aathif.taxibookingapi.repository.RiderNotificationRepository;
import com.nova.aathif.taxibookingapi.repository.RiderRepository;
import com.nova.aathif.taxibookingapi.service.RiderNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class RiderNotificationServiceImpl implements RiderNotificationService {
    @Autowired
    RiderNotificationRepository riderNotificationRepository;
    @Autowired
    RequestMetaDTO requestMetaDTO;
    @Autowired
    RiderRepository riderRepository;

    @Override
    public void saveNotification(RiderNotificationDTO riderNotificationDTO) {

        RiderNotification riderNotification = new RiderNotification();

        riderNotification.setRiderId(riderNotificationDTO.getRiderId());
        riderNotification.setNotificationTopic(riderNotificationDTO.getNotificationTopic());
        riderNotification.setNotification(riderNotificationDTO.getNotification());
        riderNotification.setDate(String.valueOf(LocalDate.now()));
        riderNotification.setTime(String.valueOf(LocalTime.now()));
        riderNotification.setShowToUser(riderNotificationDTO.getShowToUser());

        riderNotificationRepository.save(riderNotification);
    }

    @Override
    public ResponseEntity<?> riderNotification(VerificationCodeDTO verificationCodeDTO) {
        if (verificationCodeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(verificationCodeDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification");
        } else {
            List<RiderNotification> riderNotifications = riderNotificationRepository.findAllByRiderIdAndShowToUser(requestMetaDTO.getRiderId(), 1);
            return ResponseEntity.status(HttpStatus.OK).body(riderNotifications);
        }
    }
}
