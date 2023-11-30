package com.nova.aathif.taxibookingapi.service;

import com.nova.aathif.taxibookingapi.dto.ImageDTO;
import com.nova.aathif.taxibookingapi.dto.UpdateDriverVehicleDetailsDTO;
import com.nova.aathif.taxibookingapi.dto.UpdateProfileDriverDTO;
import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UpdateProfileDriver {
    ResponseEntity<?> updateProfile(UpdateProfileDriverDTO updateProfileDriverDTO);

    ResponseEntity<?> updateDriverVehicle(UpdateDriverVehicleDetailsDTO updateDriverVehicleDetailsDTO);

    ResponseEntity<?> loadDriverProfile(VerificationCodeDTO verificationCodeDTO);

    ResponseEntity<?> getDriverVehicle(VerificationCodeDTO verificationCodeDTO);

    ResponseEntity<?> profileImage(String verifications, MultipartFile file) throws IOException;

    ResponseEntity<?> driverLisenceImage1(String verification, MultipartFile file) throws IOException;

    ResponseEntity<?> driverLisenceImage2(String verification, MultipartFile file) throws IOException;

    ResponseEntity<?> driverVehicleImage1(String verification, MultipartFile file) throws IOException;

    ResponseEntity<?> driverVehicleImage2(String verification, MultipartFile file) throws IOException;
}
