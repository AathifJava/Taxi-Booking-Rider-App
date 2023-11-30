package com.nova.aathif.taxibookingapi.repository;

import com.nova.aathif.taxibookingapi.model.CustomerTrip;
import com.nova.aathif.taxibookingapi.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTripRepository extends JpaRepository<CustomerTrip, Integer> {

    List<CustomerTrip> findAllByRiderId(int riderId);
    List<CustomerTrip> findAllByRiderIdAndTripStatus(int riderId, int status);
}
