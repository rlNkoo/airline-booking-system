package com.rlnkoo.airlinecoreservice.repository;

import com.rlnkoo.airlinecoreservice.model.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    Optional<Airline> findByOwnerId(Long ownerId);
}
