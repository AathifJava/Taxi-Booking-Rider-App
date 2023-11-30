package com.nova.aathif.taxibookingapi.service.serviceImpl;

import com.nova.aathif.taxibookingapi.dto.*;
import com.nova.aathif.taxibookingapi.model.CustomerTrip;
import com.nova.aathif.taxibookingapi.model.Rider;
import com.nova.aathif.taxibookingapi.model.RiderVehicle;
import com.nova.aathif.taxibookingapi.repository.CustomerTripRepository;
import com.nova.aathif.taxibookingapi.repository.RiderRepository;
import com.nova.aathif.taxibookingapi.repository.RiderVehicleRepository;
import com.nova.aathif.taxibookingapi.repository.VehicleTypeRepository;
import com.nova.aathif.taxibookingapi.service.RiderHomeService;
import com.nova.aathif.taxibookingapi.service.RiderNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class RiderHomeServiceImpl implements RiderHomeService {

    @Autowired
    CustomerTripRepository customerTripRepository;

    @Autowired
    RiderVehicleRepository riderVehicleRepository;

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    RequestMetaDTO requestMetaDTO;
    @Autowired
    RiderNotificationService riderNotificationService;
    @Override
    public ResponseEntity<?> riderAcceptTrip(RiderAcceptTripDTO riderAcceptTripDTO) {
        if (String.valueOf(riderAcceptTripDTO.getTrip_id()).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip Id Not Found");
        } else if (String.valueOf(riderAcceptTripDTO.getVehicle_type_id()).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle Type Id Not Found");
        } else {

            Optional<CustomerTrip> optionalCustomerTrip = customerTripRepository.findById(riderAcceptTripDTO.getTrip_id());

            if (optionalCustomerTrip.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip Id Not Found");
            }

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            if (!rider.getVerification().equals(riderAcceptTripDTO.getVerification())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
            }

//            System.out.println(rider.getRiderId()+ " : 1111");

            RiderVehicle riderVehicle = riderVehicleRepository.findByRiderId(rider.getRiderId());
//            System.out.println(riderVehicle.getRiderId()+" : 2222");

            // verification
            if (!riderVehicle.getVehicleType().equals(riderAcceptTripDTO.getVehicle_type_id())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle Type Id Not Found");
            }

            CustomerTrip customerTrip = customerTripRepository.findById(riderAcceptTripDTO.getTrip_id()).get();
            customerTrip.setRiderId(rider.getRiderId());
            customerTrip.setTripAcceptTime(String.valueOf(LocalTime.now()));
            customerTrip.setTripAcceptDate(String.valueOf(LocalDate.now()));
            customerTrip.setTripStatus(2);
            customerTripRepository.save(customerTrip);

            riderNotificationService.saveNotification(new RiderNotificationDTO(
                    riderRepository.findByMobile(requestMetaDTO.getMobile()).get().getRiderId(),
                    "Rider Accept Trip Successfully",
                    "Accept Trip",
                    0
            ));

            return ResponseEntity.status(HttpStatus.OK).body("success");

        }

    }

    @Override
    public ResponseEntity<?> riderArrived(int tripId, VerificationCodeDTO verificationCodeDTO) {
        if (verificationCodeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else {

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            if (!rider.getVerification().equals(verificationCodeDTO.getVerification())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
            }

            CustomerTrip customerTrip = customerTripRepository.findById(tripId).get();
            // verification
            if (!String.valueOf(customerTrip.getRiderId()).equals(rider.getRiderId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rider Id Not Found");
            }
            customerTrip.setArrived(1);
            customerTrip.setArrivedTime(String.valueOf(LocalTime.now()));
            customerTrip.setArrivedDate(String.valueOf(LocalDate.now()));
            customerTripRepository.save(customerTrip);

            riderNotificationService.saveNotification(new RiderNotificationDTO(
                    riderRepository.findByMobile(requestMetaDTO.getMobile()).get().getRiderId(),
                    "Rider Arrived Successfully",
                    "Arrived",
                    0
            ));

            return ResponseEntity.status(HttpStatus.OK).body("Success");

        }
    }

    @Override
    public ResponseEntity<?> riderStartTrip(int tripId, RiderStartTripDTO riderStartTripDTO) {
        if (riderStartTripDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else {

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            if (!rider.getVerification().equals(riderStartTripDTO.getVerification())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
            }

            CustomerTrip customerTrip = customerTripRepository.findById(tripId).get();
            // verification
            if (!String.valueOf(customerTrip.getRiderId()).equals(rider.getRiderId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rider Id Not Found");
            }
            customerTrip.setTripStatus(3);
            customerTrip.setTripStartTime(String.valueOf(LocalTime.now()));
            customerTrip.setTripStartDate(String.valueOf(LocalDate.now()));
            customerTripRepository.save(customerTrip);

            riderNotificationService.saveNotification(new RiderNotificationDTO(
                    riderRepository.findByMobile(requestMetaDTO.getMobile()).get().getRiderId(),
                    "Rider Start Trip Successfully",
                    "Start Trip",
                    0
            ));

            return ResponseEntity.status(HttpStatus.OK).body("Success");

        }
    }

    @Override
    public ResponseEntity<?> riderEndTrip(int tripId, RiderEndTripDTO riderEndTripDTO) {
        if (riderEndTripDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else if (riderEndTripDTO.getEnd_lot().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("End Lot Not Found");
        } else if (riderEndTripDTO.getEnd_lat().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("End Lat Not Found");
        } else if (riderEndTripDTO.getTrip_distance().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Distance Not Found");
        } else {

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            if (!rider.getVerification().equals(riderEndTripDTO.getVerification())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
            }

            CustomerTrip customerTrip = customerTripRepository.findById(tripId).get();
            // verification
            if (!String.valueOf(customerTrip.getRiderId()).equals(rider.getRiderId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rider Id Not Found");
            }
            customerTrip.setTripDistance(String.valueOf(riderEndTripDTO.getTrip_distance()));
            customerTrip.setEndLat(riderEndTripDTO.getEnd_lat());
            customerTrip.setEndLot(riderEndTripDTO.getEnd_lot());
            customerTrip.setTripStatus(4);
            customerTrip.setTripEndTime(String.valueOf(LocalTime.now()));
            customerTrip.setTripEndDate(String.valueOf(LocalDate.now()));
            customerTripRepository.save(customerTrip);

            riderNotificationService.saveNotification(new RiderNotificationDTO(
                    riderRepository.findByMobile(requestMetaDTO.getMobile()).get().getRiderId(),
                    "Rider End Trip Successfully",
                    "End Trip",
                    0
            ));

            return ResponseEntity.status(HttpStatus.OK).body("Success");


        }
    }

    @Override
    public ResponseEntity<?> riderRejectTrip(int tripId, RiderRejectTripDTO riderRejectTripDTO) {
        if (riderRejectTripDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else if (riderRejectTripDTO.getReason().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reason Not Found");
        } else {

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            if (!rider.getVerification().equals(riderRejectTripDTO.getVerification())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
            }

            CustomerTrip customerTrip = customerTripRepository.findById(tripId).get();
            customerTrip.setRejectReason(riderRejectTripDTO.getReason());
            customerTrip.setRejectTime(String.valueOf(LocalTime.now()));
            customerTrip.setRejectDate(String.valueOf(LocalDate.now()));
            customerTrip.setRejectUserType(1);
            customerTrip.setTripStatus(6);
            customerTripRepository.save(customerTrip);

            riderNotificationService.saveNotification(new RiderNotificationDTO(
                    riderRepository.findByMobile(requestMetaDTO.getMobile()).get().getRiderId(),
                    "Rider Reject Trip Successfully",
                    "Reject Trip",
                    0
            ));

            return ResponseEntity.status(HttpStatus.OK).body("success");

        }
    }

    @Override
    public ResponseEntity<?> getTripDatariderById(int tripId, TripDataDTO tripDataDTO) {
        if (tripDataDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        }

        Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
        if (!rider.getVerification().equals(tripDataDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        }

        CustomerTrip customerTrip = customerTripRepository.findById(tripId).get();
        return ResponseEntity.status(HttpStatus.OK).body(customerTrip);
    }

    @Override
    public ResponseEntity<?> switchDriverMode(int tripId, SwitchDriverModeDTO switchDriverModeDTO) {
        if (switchDriverModeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
        } else if (String.valueOf(switchDriverModeDTO.getMode()).equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mode Not Found");
        } else {

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();
            if (!rider.getVerification().equals(switchDriverModeDTO.getVerification())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Code Not Found");
            }

            if (String.valueOf(rider.getImLive()).equals("0")) {
                rider.setImLive(switchDriverModeDTO.getMode());
                riderRepository.save(rider);

                riderNotificationService.saveNotification(new RiderNotificationDTO(
                        riderRepository.findByMobile(requestMetaDTO.getMobile()).get().getRiderId(),
                        "Driver ONLINE Successfully",
                        "Driver Mode",
                        0
                ));

                return ResponseEntity.status(HttpStatus.OK).body("GO ONLINE");
            } else {
                rider.setImLive(switchDriverModeDTO.getMode());
                riderRepository.save(rider);

                riderNotificationService.saveNotification(new RiderNotificationDTO(
                        riderRepository.findByMobile(requestMetaDTO.getMobile()).get().getRiderId(),
                        "Driver OFFLINE Successfully",
                        "Driver Mode",
                        0
                ));

                return ResponseEntity.status(HttpStatus.OK).body("GO OFFLINE");
            }

        }
    }


}
