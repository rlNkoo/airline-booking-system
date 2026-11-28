package com.rlnkoo.airlinecoreservice.service;

import com.rlnkoo.payload.request.AircraftRequest;
import com.rlnkoo.payload.response.AircraftResponse;

import java.util.List;

public interface AircraftService {

    AircraftResponse createAircraft(AircraftRequest aircraft, Long ownerId) throws Exception;

    AircraftResponse getAircraftById(Long id) throws Exception;

    List<AircraftResponse> listAllAircraftByOwner(Long ownerId) throws Exception;

    AircraftResponse updateAircraft(Long id, AircraftRequest aircraft, Long ownerId) throws Exception;

    void deleteAircraft(Long id,  Long ownerId) throws Exception;
}