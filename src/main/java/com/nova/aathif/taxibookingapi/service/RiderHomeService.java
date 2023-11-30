package com.nova.aathif.taxibookingapi.service;

import com.nova.aathif.taxibookingapi.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RiderHomeService {
    ResponseEntity<?> riderAcceptTrip(RiderAcceptTripDTO riderAcceptTripDTO);

    ResponseEntity<?> riderArrived(int tripId, VerificationCodeDTO verificationCodeDTO);

    ResponseEntity<?> riderStartTrip(int tripId, RiderStartTripDTO riderStartTripDTO);

    ResponseEntity<?> riderEndTrip(int tripId, RiderEndTripDTO riderEndTripDTO);

    ResponseEntity<?> riderRejectTrip(int tripId, RiderRejectTripDTO riderRejectTripDTO);

    ResponseEntity<?> getTripDatariderById(int tripId, TripDataDTO tripDataDTO);

    ResponseEntity<?> switchDriverMode(int tripId, SwitchDriverModeDTO switchDriverModeDTO);
}
