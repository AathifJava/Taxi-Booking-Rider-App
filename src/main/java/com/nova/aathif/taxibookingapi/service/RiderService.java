package com.nova.aathif.taxibookingapi.service;

import com.nova.aathif.taxibookingapi.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RiderService {
    ResponseEntity<?> registerRider(RiderRegisterDTO riderRegisterDTO);

    ResponseEntity<?> getVerificanCode(RiderOtpDTO riderOtpDTO);

    ResponseEntity<?> verifiRiderMobile(VerifyOtpDTO verifyOtpDTO);

    ResponseEntity<?> vehicleTypes();

    ResponseEntity<?> resetPassword(ResetPasswordDTO resetPasswordDTO);

    ResponseEntity<?> login(RiderLoginDTO riderLoginDTO);
}
