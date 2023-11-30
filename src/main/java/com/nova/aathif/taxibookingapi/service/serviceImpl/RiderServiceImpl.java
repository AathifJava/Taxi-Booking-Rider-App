package com.nova.aathif.taxibookingapi.service.serviceImpl;

import com.nova.aathif.taxibookingapi.dto.*;
import com.nova.aathif.taxibookingapi.model.Rider;
import com.nova.aathif.taxibookingapi.model.RiderLoginHistory;
import com.nova.aathif.taxibookingapi.model.RiderVehicle;
import com.nova.aathif.taxibookingapi.model.VehicleType;
import com.nova.aathif.taxibookingapi.repository.RiderLoginHistoryRepository;
import com.nova.aathif.taxibookingapi.repository.RiderRepository;
import com.nova.aathif.taxibookingapi.repository.RiderVehicleRepository;
import com.nova.aathif.taxibookingapi.repository.VehicleTypeRepository;
import com.nova.aathif.taxibookingapi.service.RiderNotificationService;
import com.nova.aathif.taxibookingapi.service.RiderService;
import com.nova.aathif.taxibookingapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class RiderServiceImpl implements RiderService {

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    RiderVehicleRepository riderVehicleRepository;

    @Autowired
    RiderLoginHistoryRepository riderLoginHistoryRepository;

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    RiderNotificationService riderNotificationService;

    @Override
    public ResponseEntity<?> registerRider(RiderRegisterDTO riderRegisterDTO) {
        if (riderRegisterDTO.getMobile().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mobile Not Found!");
        } else if (riderRegisterDTO.getPassword().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password Not Found!");
        } else if (riderRegisterDTO.getName().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Name Not Found!");
        } else if (String.valueOf(riderRegisterDTO.getVehicle_type_id()).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle Type Not Found!");
        } else if (riderRegisterDTO.getPlate_no().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plate No Not Found!");
        } else if (riderRegisterDTO.getLisence_no().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lisence No Not Found!");
        } else if (riderRegisterDTO.getNic().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NIC Not Found!");
        } else if (riderRegisterDTO.getAddress().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address Not Found!");
        } else if (riderRegisterDTO.getNotification_key().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification Key Not Found!");
        } else {

            Optional<Rider> optionalRider = riderRepository.findByMobile(riderRegisterDTO.getMobile());

            if (optionalRider.isEmpty()) {
                Rider rider = new Rider();
                rider.setMobile(riderRegisterDTO.getMobile());
                rider.setPassword(riderRegisterDTO.getPassword());
                rider.setUsername(riderRegisterDTO.getName());
                rider.setNic(riderRegisterDTO.getNic());
                rider.setAddress(riderRegisterDTO.getAddress());
                rider.setNotificationKey(riderRegisterDTO.getNotification_key());
                rider.setLicenceNo(riderRegisterDTO.getLisence_no());
                rider.setStatus(3);
                riderRepository.save(rider);

                RiderVehicle riderVehicle = new RiderVehicle();
                riderVehicle.setRiderId(rider.getRiderId());
                riderVehicle.setVehicleTypeId(riderRegisterDTO.getVehicle_type_id());
                riderVehicle.setPlateNo(riderRegisterDTO.getPlate_no());
                riderVehicleRepository.save(riderVehicle);

                riderNotificationService.saveNotification(new RiderNotificationDTO(
                        riderRepository.findByMobile(riderRegisterDTO.getMobile()).get().getRiderId(),
                        "Account Created Successfully",
                        "New Rider Registration",
                        0
                ));

            } else if (String.valueOf(optionalRider.get().getStatus()).equals("0")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Blocklisted account. cannot Login to account");
            }

            return ResponseEntity.status(HttpStatus.OK).body("Success");


        }

    }

    @Override
    public ResponseEntity<?> getVerificanCode(RiderOtpDTO riderOtpDTO) {
        if (riderOtpDTO.getMobile().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mobile Not Found");
        } else {
            Optional<Rider> optionalRider = riderRepository.findByMobile(riderOtpDTO.getMobile());
            if (optionalRider.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Mobile");
            } else {
                String verificationCode = String.format("%06d", new Random().nextInt(999999));
                Rider rider = optionalRider.get();
                rider.setVerification(verificationCode);
                riderRepository.save(rider);
                return ResponseEntity.status(HttpStatus.OK).body(verificationCode);
            }
        }
    }

    @Override
    public ResponseEntity<?> verifiRiderMobile(VerifyOtpDTO verifyOtpDTO) {
        if (verifyOtpDTO.getMobile().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mobile Not Found");
        } else if (verifyOtpDTO.getCode().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTP Not Found");
        } else {

            Optional<Rider> optionalRider = riderRepository.findByMobile(verifyOtpDTO.getMobile());
            if (optionalRider.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mobile Number Not Found. Try Again!");
            } else {
                Rider rider = optionalRider.get();
                if (!rider.getVerification().equals(verifyOtpDTO.getCode())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Entered OTP is Wrong. Try Again!");
                }
                rider.setStatus(1);
                riderRepository.save(rider);
                String accessToken = jwtUtil.generateAccessToken(rider);
                Map<String, String> data = new HashMap<>();
                data.put("message", "Good to go");
                data.put("accessToken", accessToken);

                riderNotificationService.saveNotification(new RiderNotificationDTO(
                        riderRepository.findByMobile(verifyOtpDTO.getMobile()).get().getRiderId(),
                        "Verify Rider Successfully",
                        "Verify",
                        0
                ));

                return ResponseEntity.status(HttpStatus.OK).body(data);
            }
        }
    }

    @Override
    public ResponseEntity<?> vehicleTypes() {
        List<VehicleType> vehicleTypes = vehicleTypeRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(vehicleTypes);
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        if (resetPasswordDTO.getMobile().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mobile Not Found!");
        } else if (resetPasswordDTO.getCode().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("verification Code Not Found!");
        } else if (resetPasswordDTO.getNewPassword().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("New Password Not Found!");
        } else {
            Optional<Rider> optionalRider = riderRepository.findByMobile(resetPasswordDTO.getMobile());
            if (optionalRider.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unauthorized");
            } else {
                Rider rider = optionalRider.get();
                if (rider.getVerification().equals(resetPasswordDTO.getCode())) {
                    rider.setPassword(resetPasswordDTO.getNewPassword());
                    riderRepository.save(rider);

                    riderNotificationService.saveNotification(new RiderNotificationDTO(
                            riderRepository.findByMobile(resetPasswordDTO.getMobile()).get().getRiderId(),
                            "Reset Password Successfully",
                            "Reset Password",
                            0
                    ));

                    return ResponseEntity.status(HttpStatus.OK).body("ok");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("wrong pin");
                }
            }
        }
    }

    @Override
    public ResponseEntity<?> login(RiderLoginDTO riderLoginDTO) {
        if (riderLoginDTO.getMobile().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mobile Not Found!");
        } else if (riderLoginDTO.getPassword().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password Not Found!");
        } else if (riderLoginDTO.getNotificationKey().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification Key Not Found!");
        } else {
            Optional<Rider> optionalRider = riderRepository.findByMobile(riderLoginDTO.getMobile());
            if (optionalRider.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid username password");
            } else {
                Rider rider = optionalRider.get();
                if (rider.getPassword().equals(riderLoginDTO.getPassword())) {
                    if (String.valueOf(rider.getStatus()).equals("1")) {
                        rider.setNotificationKey(riderLoginDTO.getNotificationKey());
                        String verificationCode = String.format("%06d", new Random().nextInt(999999));
                        rider.setVerification(verificationCode);
                        riderRepository.save(rider);

                        String accessToken = jwtUtil.generateAccessToken(rider);
                        Map<String, String> data = new HashMap<>();
                        data.put("message", "Good to go");
                        data.put("accessToken", accessToken);

                        RiderLoginHistory riderLoginHistory = new RiderLoginHistory();
                        riderLoginHistory.setRiderId(rider.getRiderId());
                        riderLoginHistory.setStatus(rider.getStatus());
                        riderLoginHistory.setTime(String.valueOf(LocalTime.now()));
                        riderLoginHistory.setDate(String.valueOf(LocalDate.now()));
                        riderLoginHistoryRepository.save(riderLoginHistory);

                        riderNotificationService.saveNotification(new RiderNotificationDTO(
                                riderRepository.findByMobile(riderLoginDTO.getMobile()).get().getRiderId(),
                                "Login Successfully",
                                "Rider Login",
                                0
                        ));

                        return ResponseEntity.status(HttpStatus.OK).body(data);
                    } else if (String.valueOf(rider.getStatus()).equals("5")) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("account is blacklistyed");
                    } else if (String.valueOf(rider.getStatus()).equals("3")) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("mobile not verified yet, forward to verification screen");
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unauthorized");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid username password");
                }
            }
        }
    }
}
