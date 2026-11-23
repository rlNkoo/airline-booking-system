package com.rlnkoo.locationservice.service;

import com.rlnkoo.payload.request.AirportRequest;
import com.rlnkoo.payload.response.AirportResponse;

import java.util.List;

public interface AirportService {

    AirportResponse createAirport(AirportRequest request);

    AirportResponse getAirportById(Long id);

    List<AirportResponse> getAllAirports();

    AirportResponse updateAirport(Long id, AirportRequest request);

    void deleteAirport(Long id);

    List<AirportResponse> getAirportByCityId(Long cityId);
}