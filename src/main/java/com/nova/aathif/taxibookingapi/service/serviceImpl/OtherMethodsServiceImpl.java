package com.nova.aathif.taxibookingapi.service.serviceImpl;

import com.nova.aathif.taxibookingapi.dto.RequestMetaDTO;
import com.nova.aathif.taxibookingapi.dto.SendMessageRiderDTO;
import com.nova.aathif.taxibookingapi.dto.TripHistoryByRiderDTO;
import com.nova.aathif.taxibookingapi.dto.UpdateRiderCurrentLocationDTO;
import com.nova.aathif.taxibookingapi.model.CustomerTrip;
import com.nova.aathif.taxibookingapi.model.Rider;
import com.nova.aathif.taxibookingapi.model.RiderContact;
import com.nova.aathif.taxibookingapi.model.RiderVehicle;
import com.nova.aathif.taxibookingapi.repository.CustomerTripRepository;
import com.nova.aathif.taxibookingapi.repository.RiderContactRepository;
import com.nova.aathif.taxibookingapi.repository.RiderRepository;
import com.nova.aathif.taxibookingapi.service.OtherMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OtherMethodsServiceImpl implements OtherMethodsService {

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    CustomerTripRepository customerTripRepository;

    @Autowired
    RequestMetaDTO requestMetaDTO;

    @Autowired
    RiderContactRepository riderContactRepository;

    @Override
    public ResponseEntity<?> getTripHistory(TripHistoryByRiderDTO tripHistoryByRiderDTO) {
        if (tripHistoryByRiderDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(tripHistoryByRiderDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification");
        } else {

            String from = tripHistoryByRiderDTO.getFrom();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<CustomerTrip> customerTrips = new ArrayList<>();
            try {
                Date date = dateFormat.parse(from);
                List<CustomerTrip> allTrips = customerTripRepository.findAllByRiderId(requestMetaDTO.getRiderId());
                allTrips.forEach(trip -> {
                    try {
                        Date tripDate = dateFormat.parse(trip.getTripDate());
                        if (tripDate.equals(date) || tripDate.after(date)) {
                            customerTrips.add(trip);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.OK).body(customerTrips);
        }
    }

    @Override
    public ResponseEntity<?> sendMessage(SendMessageRiderDTO sendMessageRiderDTO) {
        if (sendMessageRiderDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(sendMessageRiderDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification");
        } else if (sendMessageRiderDTO.getMessage().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message Not Found");
        } else {

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();

            RiderContact riderContact = new RiderContact();
            riderContact.setRiderId(rider.getRiderId());
            riderContact.setMessage(sendMessageRiderDTO.getMessage());
            riderContact.setEmail(riderContact.getEmail());
            riderContact.setDate(String.valueOf(LocalDate.now()));
            riderContact.setTime(String.valueOf(LocalTime.now()));
            riderContact.setStatus(1);

            riderContactRepository.save(riderContact);
            return ResponseEntity.status(HttpStatus.OK).body("success");

        }
    }

    @Override
    public ResponseEntity<?> updateRiderLocation(UpdateRiderCurrentLocationDTO updateRiderCurrentLocationDTO) {
        if (updateRiderCurrentLocationDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(updateRiderCurrentLocationDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification");
        } else if (updateRiderCurrentLocationDTO.getLat().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Currant Lat Not Found");
        } else if (updateRiderCurrentLocationDTO.getLon().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Currant Lon Not Found");
        } else {

            Rider rider = riderRepository.findById(requestMetaDTO.getRiderId()).get();

            rider.setCurrentLat(updateRiderCurrentLocationDTO.getLat());
            rider.setCurrentLon(updateRiderCurrentLocationDTO.getLon());
            riderRepository.save(rider);
            return ResponseEntity.status(HttpStatus.OK).body("success");
        }
    }
}
