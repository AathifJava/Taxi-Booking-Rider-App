package com.nova.aathif.taxibookingapi.repository;

import com.nova.aathif.taxibookingapi.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
}
