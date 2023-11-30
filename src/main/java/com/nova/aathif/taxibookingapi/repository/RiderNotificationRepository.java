package com.nova.aathif.taxibookingapi.repository;

import com.nova.aathif.taxibookingapi.model.RiderNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiderNotificationRepository extends JpaRepository<RiderNotification, Integer> {
    List<RiderNotification> findAllByRiderIdAndShowToUser(int riderId, int i);
}
