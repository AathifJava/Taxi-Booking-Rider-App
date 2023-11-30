package com.nova.aathif.taxibookingapi.service.serviceImpl;

import com.nova.aathif.taxibookingapi.dto.PaymentDTO;
import com.nova.aathif.taxibookingapi.dto.PaymentHistoryDTO;
import com.nova.aathif.taxibookingapi.dto.RequestMetaDTO;
import com.nova.aathif.taxibookingapi.dto.VerificationCodeDTO;
import com.nova.aathif.taxibookingapi.model.CustomerTrip;
import com.nova.aathif.taxibookingapi.repository.CustomerTripRepository;
import com.nova.aathif.taxibookingapi.repository.RiderRepository;
import com.nova.aathif.taxibookingapi.service.PaymentScreenRiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaymentScreenRiderServiceImpl implements PaymentScreenRiderService {
    @Autowired
    RiderRepository riderRepository;
    @Autowired
    RequestMetaDTO requestMetaDTO;
    @Autowired
    CustomerTripRepository customerTripRepository;

    @Override
    public ResponseEntity<?> paymentHistory(PaymentHistoryDTO paymentHistoryDTO) {
        if (paymentHistoryDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(paymentHistoryDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification");
        } else if (paymentHistoryDTO.getStart_date().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start Date Not Found");
        } else if (paymentHistoryDTO.getEnd_date().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End Date Not Found");
        } else {

            String startDate = paymentHistoryDTO.getStart_date();
            String endDate = paymentHistoryDTO.getEnd_date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<PaymentDTO> customerTrips = new ArrayList<>();

            try {
                Date startDate1 = dateFormat.parse(startDate);
                Date endDate1 = dateFormat.parse(endDate);

                List<CustomerTrip> allTrips = customerTripRepository.findAllByRiderId(requestMetaDTO.getRiderId());
                allTrips.forEach(trip -> {
                    try {
                        Date tripDate = dateFormat.parse(trip.getTripEndDate());
                        if (tripDate.after(startDate1) && tripDate.before(endDate1)) {

                            PaymentDTO paymentDTO = new PaymentDTO(trip.getTripCharge(), trip.getCompanyIncome(), trip.getDriverIncome());
                            customerTrips.add(paymentDTO);

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
    public ResponseEntity<?> todayAccountHisotry(VerificationCodeDTO verificationCodeDTO) {
        if (verificationCodeDTO.getVerification().equals("")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Verification Not Found");
        } else if (!riderRepository.findById(requestMetaDTO.getRiderId()).get().getVerification().equals(verificationCodeDTO.getVerification())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification");
        } else {

            String date = String.valueOf(LocalDate.now());

            List<CustomerTrip> allByRiderId = customerTripRepository.findAllByRiderId(requestMetaDTO.getRiderId());
            List<CustomerTrip> trip = new ArrayList<>();
            allByRiderId.forEach(trips -> {
                if (trips.getTripEndDate().equals(date)) {
                    trip.add(trips);
                }
            });
            return ResponseEntity.status(HttpStatus.OK).body(trip);
        }
    }
}
