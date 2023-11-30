package com.nova.aathif.taxibookingapi.repository;

import com.nova.aathif.taxibookingapi.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Integer> {
    Optional<Rider> findByMobile(String mobile);
}
