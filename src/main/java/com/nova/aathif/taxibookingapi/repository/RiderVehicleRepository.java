package com.nova.aathif.taxibookingapi.repository;

import com.nova.aathif.taxibookingapi.model.RiderVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RiderVehicleRepository extends JpaRepository<RiderVehicle, Integer> {

    RiderVehicle findByRiderId(int riderId);
}
