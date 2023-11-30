package com.nova.aathif.taxibookingapi.service;

import com.nova.aathif.taxibookingapi.dto.SendMessageRiderDTO;
import com.nova.aathif.taxibookingapi.dto.TripHistoryByRiderDTO;
import com.nova.aathif.taxibookingapi.dto.UpdateRiderCurrentLocationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OtherMethodsService {
    ResponseEntity<?> getTripHistory(TripHistoryByRiderDTO tripHistoryByRiderDTO);

    ResponseEntity<?> sendMessage(SendMessageRiderDTO sendMessageRiderDTO);

    ResponseEntity<?> updateRiderLocation(UpdateRiderCurrentLocationDTO updateRiderCurrentLocationDTO);
}
