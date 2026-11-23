package com.rlnkoo.locationservice.repository;

import com.rlnkoo.locationservice.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    Airport findByIataCode(String iataCode);

    List<Airport> findByCityId(Long cityId);
}