package com.nova.aathif.taxibookingapi.repository;

import com.nova.aathif.taxibookingapi.model.RiderContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderContactRepository extends JpaRepository<RiderContact, Integer> {
}
