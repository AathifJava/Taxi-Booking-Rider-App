package com.nova.aathif.taxibookingapi.service.serviceImpl;

import com.nova.aathif.taxibookingapi.dto.*;
import com.nova.aathif.taxibookingapi.model.Rider;
import com.nova.aathif.taxibookingapi.model.RiderVehicle;
import com.nova.aathif.taxibookingapi.repository.RiderRepository;
import com.nova.aathif.taxibookingapi.repository.RiderVehicleRepository;
import com.nova.aathif.taxibookingapi.service.RiderNotificationService;
import com.nova.aathif.taxibookingapi.service.UpdateProfileDriver;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.*;
import java.util.Optional;

@Service
public class UpdateProfileDriverImpl implements UpdateProfileDriver {

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    RequestMetaDTO requestMetaDTO;

    @Autowired
    RiderVehicleRepository riderVehicleRepository;

    @Autowired
    RiderNotificationService riderNotificationService;

    public static final String UPLOAD_FILE_NAME = "upload";

    @Override
    public ResponseEntity<?> updateProfile(UpdateProfileDriverDTO updateProfileDriverDTO) {
        if (updateProfileDriverDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(updateProfileDriverDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else {

            Optional<Rider> optionalRider = riderRepository.findById(requestMetaDTO.getRiderId());
            if (optionalRider.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Id");
            }

            Rider rider = optionalRider.get();
            if (!rider.getVerification().equals(updateProfileDriverDTO.getVerification())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
            }

            rider.setRiderName(updateProfileDriverDTO.getName());
            rider.setAddress(updateProfileDriverDTO.getAddress());
            rider.setBirthday(updateProfileDriverDTO.getBirthday());
            rider.setHomeMobile(updateProfileDriverDTO.getEmergency_mobile());
            rider.setEmail(updateProfileDriverDTO.getEmail());
            rider.setGender(updateProfileDriverDTO.getGender());
            rider.setLicenceNo(updateProfileDriverDTO.getLisence_number());
            riderRepository.save(rider);

            riderNotificationService.saveNotification(new RiderNotificationDTO(
                    riderRepository.findByMobile(requestMetaDTO.getMobile()).get().getRiderId(),
                    "Rider Update Profile Successfully",
                    "Update Profile",
                    0
            ));

            return ResponseEntity.status(HttpStatus.OK).body("success");
        }
    }

    @Override
    public ResponseEntity<?> updateDriverVehicle(UpdateDriverVehicleDetailsDTO updateDriverVehicleDetailsDTO) {
        if (updateDriverVehicleDetailsDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(updateDriverVehicleDetailsDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else {

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();

            RiderVehicle riderId = riderVehicleRepository.findByRiderId(rider.getRiderId());
            if (riderId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rider Not Found");
            }

            riderId.setPlateNo(updateDriverVehicleDetailsDTO.getPlateNo());
            riderId.setColor(updateDriverVehicleDetailsDTO.getColor());
            riderId.setRegisterNo(updateDriverVehicleDetailsDTO.getRegisterNo());
            riderId.setManufactureYear(updateDriverVehicleDetailsDTO.getManufactureYear());
            riderVehicleRepository.save(riderId);

            riderNotificationService.saveNotification(new RiderNotificationDTO(
                    riderRepository.findByMobile(requestMetaDTO.getMobile()).get().getRiderId(),
                    "Rider Update Driver Vehicle Successfully",
                    "Update Driver Vehicle",
                    0
            ));

            return ResponseEntity.status(HttpStatus.OK).body("success");

        }
    }

    @Override
    public ResponseEntity<?> loadDriverProfile(VerificationCodeDTO verificationCodeDTO) {

        if (verificationCodeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        }

        Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
        if (!rider.getVerification().equals(verificationCodeDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(rider);
    }

    @Override
    public ResponseEntity<?> getDriverVehicle(VerificationCodeDTO verificationCodeDTO) {
        if (verificationCodeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        }

        Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
        if (!rider.getVerification().equals(verificationCodeDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        }

        RiderVehicle riderVehicle = riderVehicleRepository.findByRiderId(rider.getRiderId());
        if (riderVehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rider Not Found");
        }


        return ResponseEntity.status(HttpStatus.OK).body(riderVehicle);
    }

    @Override
    public ResponseEntity<?> profileImage(String verification, MultipartFile file) throws IOException {
        if (verification.equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(verification)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please Select the Image");
        } else {

            Path path = Paths.get(UPLOAD_FILE_NAME);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "." + extension;

            Path filePath = path.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String appUrl = String.format("http://%s:%S", InetAddress.getLocalHost().getHostName(), 8080);
            String url = UPLOAD_FILE_NAME + "/" + fileName;
            String fullUrl = appUrl + "/" + url;

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            rider.setProfileImage(url);
            riderRepository.save(rider);

            return ResponseEntity.status(HttpStatus.OK).body("Success");

        }
    }

    @Override
    public ResponseEntity<?> driverLisenceImage1(String verification, MultipartFile file) throws IOException {

        if (verification.equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(verification)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please Select the Image");
        } else {

            Path path = Paths.get(UPLOAD_FILE_NAME);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "." + extension;

            Path filePath = path.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String appUrl = String.format("http://%s:%S", InetAddress.getLocalHost().getHostName(), 8080);
            String url = UPLOAD_FILE_NAME + "/" + fileName;
            String fullUrl = appUrl + "/" + url;


            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            rider.setLicenceImage1(url);
            riderRepository.save(rider);

            return ResponseEntity.status(HttpStatus.OK).body("Success");

        }

    }

    @Override
    public ResponseEntity<?> driverLisenceImage2(String verification, MultipartFile file) throws IOException {
        if (verification.equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(verification)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please Select the Image");
        } else {

            Path path = Paths.get(UPLOAD_FILE_NAME);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "." + extension;

            Path filePath = path.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String appUrl = String.format("http://%s:%S", InetAddress.getLocalHost().getHostName(), 8080);
            String url = UPLOAD_FILE_NAME + "/" + fileName;
            String fullUrl = appUrl + "/" + url;


            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            rider.setLicenceImage2(url);
            riderRepository.save(rider);

            return ResponseEntity.status(HttpStatus.OK).body("Success");

        }
    }

    @Override
    public ResponseEntity<?> driverVehicleImage1(String verification, MultipartFile file) throws IOException {
        if (verification.equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(verification)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please Select the Image");
        } else {

            Path path = Paths.get(UPLOAD_FILE_NAME);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "." + extension;

            Path filePath = path.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String appUrl = String.format("http://%s:%S", InetAddress.getLocalHost().getHostName(), 8080);
            String url = UPLOAD_FILE_NAME + "/" + fileName;
            String fullUrl = appUrl + "/" + url;


            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            RiderVehicle riderId = riderVehicleRepository.findByRiderId(rider.getRiderId());
            riderId.setVehicleImage(url);
            riderVehicleRepository.save(riderId);

            return ResponseEntity.status(HttpStatus.OK).body("Success");

        }
    }

    @Override
    public ResponseEntity<?> driverVehicleImage2(String verification, MultipartFile file) throws IOException {
        if (verification.equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(verification)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please Select the Image");
        } else {

            Path path = Paths.get(UPLOAD_FILE_NAME);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "." + extension;

            Path filePath = path.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String appUrl = String.format("http://%s:%S", InetAddress.getLocalHost().getHostName(), 8080);
            String url = UPLOAD_FILE_NAME + "/" + fileName;
            String fullUrl = appUrl + "/" + url;


            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            RiderVehicle riderId = riderVehicleRepository.findByRiderId(rider.getRiderId());
            riderId.setVehicleImage2(url);
            riderVehicleRepository.save(riderId);

            return ResponseEntity.status(HttpStatus.OK).body("Success");

        }
    }


}
