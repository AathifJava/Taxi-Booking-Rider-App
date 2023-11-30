package com.nova.aathif.taxibookingapi.service.serviceImpl;

import com.nova.aathif.taxibookingapi.dto.RequestMetaDTO;
import com.nova.aathif.taxibookingapi.dto.RiderDTO;
import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import com.nova.aathif.taxibookingapi.model.CustomerTrip;
import com.nova.aathif.taxibookingapi.model.Rider;
import com.nova.aathif.taxibookingapi.model.RiderVehicle;
import com.nova.aathif.taxibookingapi.repository.CustomerTripRepository;
import com.nova.aathif.taxibookingapi.repository.RiderRepository;
import com.nova.aathif.taxibookingapi.service.FlashScreenMethodsService;
import com.nova.aathif.taxibookingapi.service.RiderNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlashScreenMethodsServiceImpl implements FlashScreenMethodsService {
    @Autowired
    RiderRepository riderRepository;
    @Autowired
    RequestMetaDTO requestMetaDTO;
    @Autowired
    CustomerTripRepository customerTripRepository;
    @Autowired
    RiderNotificationService riderNotificationService;
    @Override
    public ResponseEntity<?> checkPendingTrip(VerificationCodeDTO verificationCodeDTO) {
        if (verificationCodeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(verificationCodeDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification");
        } else {

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            List<CustomerTrip> allByRiderIdAndTripStatus = customerTripRepository.findAllByRiderIdAndTripStatus(rider.getRiderId(), 1);

            return ResponseEntity.status(HttpStatus.OK).body(allByRiderIdAndTripStatus);

        }
    }

    @Override
    public ResponseEntity<?> getRiderData(VerificationCodeDTO verificationCodeDTO) {
        if (verificationCodeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(verificationCodeDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification");
        } else {

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            RiderDTO riderDTO = new RiderDTO();
            riderDTO.setName(rider.getRiderName());
            riderDTO.setMobile(rider.getMobile());
            riderDTO.setEmail(rider.getEmail());
            riderDTO.setNotify(rider.getNotificationKey());

            return ResponseEntity.status(HttpStatus.OK).body(riderDTO);

        }
    }
}
