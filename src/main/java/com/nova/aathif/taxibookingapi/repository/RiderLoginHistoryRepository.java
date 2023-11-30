package com.nova.aathif.taxibookingapi.repository;

import com.nova.aathif.taxibookingapi.model.RiderLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderLoginHistoryRepository extends JpaRepository<RiderLoginHistory, Integer> {
}
